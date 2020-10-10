package com.joh.esms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerOrderDAO;
import com.joh.esms.dao.ProductDAO;
import com.joh.esms.dao.ProductStepUpDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.domain.model.ProductD;
import com.joh.esms.exception.ItemNotAvaiableException;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.CustomerOrder;
import com.joh.esms.model.CustomerOrderDetail;
import com.joh.esms.model.CustomerOrderDetailProductStepUp;
import com.joh.esms.model.Product;
import com.joh.esms.model.ProductStepUp;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private static final Logger logger = Logger.getLogger(CustomerOrderServiceImpl.class);

    @Autowired
    private CustomerOrderDAO customerOrderDAO;

    @Autowired
    private ProductStepUpDAO productStepUpDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Autowired
    private ProductStockService productStockService;

    @Override
    @Transactional
    public CustomerOrder save(CustomerOrder customerOrder) {
        double totalPrice = 0;
        for (CustomerOrderDetail customerOrderDetail : customerOrder.getCustomerOrderDetails()) {
            Product product = null;

            ProductD productD = productDAO.findProductByCode(customerOrderDetail.getProductCode());

            totalPrice += customerOrderDetail.getQuantity() * customerOrderDetail.getPrice();
            logger.info("customerOrderDetail.getPrice()=" + customerOrderDetail.getPrice());

            int i = 0;
            for (i = 0; i < customerOrderDetail.getQuantity().intValue(); i++) {
                int amount = 1;
                final ProductStepUp itemForStockDown = productStepUpDAO
                        .findProductStepUpForStockDown(customerOrderDetail.getProductCode(), amount);

                if (itemForStockDown == null) {
                    String message = String.format("This product (%s) is not available enough in the stock",
                            customerOrderDetail.getProductCode());

                    throw new ItemNotAvaiableException(message);
                }

                if (i == 0 && itemForStockDown != null) {
                    product = itemForStockDown.getProduct();
                    customerOrderDetail.setProduct(product);
                }

                ProductStepUp productStepUp = new ProductStepUp();
                productStepUp.setId(itemForStockDown.getId());

                Optional<CustomerOrderDetailProductStepUp> alreadySet = customerOrderDetail
                        .getCustomerOrderDetailProductStepUps().stream()
                        .filter(e -> e.getProductStepUp().getId() == itemForStockDown.getId()).findFirst();

                if (alreadySet.isPresent()) {
                    CustomerOrderDetailProductStepUp customerOrderDetailProductStepUp = alreadySet.get();
                    customerOrderDetailProductStepUp.setAmount(customerOrderDetailProductStepUp.getAmount() + amount);
                } else {
                    CustomerOrderDetailProductStepUp customerOrderDetailProductStepUp = new CustomerOrderDetailProductStepUp();
                    customerOrderDetailProductStepUp.setProductStepUp(productStepUp);
                    customerOrderDetailProductStepUp.setAmount((double) amount);
                    customerOrderDetail.getCustomerOrderDetailProductStepUps().add(customerOrderDetailProductStepUp);
                }

                customerOrderDetail.setCost(productD.getCost());
                productStepUpDAO.stockDown(itemForStockDown.getId(), amount);
            }

            // Check if any remain at the end eg.123.233 = 0.233
            if (customerOrderDetail.getQuantity() % 1 > 0) {

                double amount = customerOrderDetail.getQuantity() % 1;
                final ProductStepUp itemForStockDown = productStepUpDAO
                        .findProductStepUpForStockDown(customerOrderDetail.getProductCode(), amount);

                if (itemForStockDown == null) {
                    String message = String.format("This product (%s) is not available enough in the stock",
                            customerOrderDetail.getProductCode());
                    throw new ItemNotAvaiableException(message);
                }

                if (i == 0 && itemForStockDown != null) {
                    product = itemForStockDown.getProduct();
                    customerOrderDetail.setProduct(product);
                }

                ProductStepUp productStepUp = new ProductStepUp();
                productStepUp.setId(itemForStockDown.getId());

                Optional<CustomerOrderDetailProductStepUp> alreadySet = customerOrderDetail
                        .getCustomerOrderDetailProductStepUps().stream()
                        .filter(e -> e.getProductStepUp().getId() == itemForStockDown.getId()).findFirst();

                if (alreadySet.isPresent()) {
                    CustomerOrderDetailProductStepUp customerOrderDetailProductStepUp = alreadySet.get();
                    customerOrderDetailProductStepUp.setAmount(customerOrderDetailProductStepUp.getAmount() + amount);
                } else {
                    CustomerOrderDetailProductStepUp customerOrderDetailProductStepUp = new CustomerOrderDetailProductStepUp();
                    customerOrderDetailProductStepUp.setProductStepUp(productStepUp);
                    customerOrderDetailProductStepUp.setAmount(amount);
                    customerOrderDetail.getCustomerOrderDetailProductStepUps().add(customerOrderDetailProductStepUp);
                }

                customerOrderDetail.setCost(productD.getCost());
                productStepUpDAO.stockDown(itemForStockDown.getId(), amount);

            }

        }

        totalPrice = (double) Math.round(totalPrice * 1000d) / 1000d;

        customerOrder.setTotalPrice(totalPrice);

        customerOrder = customerOrderDAO.save(customerOrder);

        accountTransactionService.makeTransaction(AccountTransactionType.CUSTOMER_ORDER, customerOrder.getId(),
                customerOrder.getTotalPayment());

        customerOrder.getCustomerOrderDetails().forEach(e -> {
            productStockService.stepDown(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
        });

        return customerOrder;
    }

    @Override
    public CustomerOrder findOne(int id) {
        CustomerOrder customerOrder = customerOrderDAO.findOne(id);
        if (customerOrder == null)
            throw new EntityNotFoundException("" + id);
        return customerOrder;
    }

    @Override
    public CustomerOrder findByInvoiceId(int id) {
        CustomerOrder customerOrder = customerOrderDAO.findByInvoiceId(id);
        if (customerOrder == null)
            throw new EntityNotFoundException("" + id);
        return customerOrder;
    }

    @Override
    @Transactional
    public CustomerOrder update(CustomerOrder customerOrder) {

        CustomerOrder savedCustomerOrder = customerOrderDAO.findOne(customerOrder.getId());

        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(customerOrder.getId(),
                AccountTransactionType.CUSTOMER_ORDER);
        if (accountTransaction != null)
            accountTransactionService.delete(accountTransaction.getId());

        savedCustomerOrder.getCustomerOrderDetails().forEach(e -> {
            productStockService.stepUp(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
        });

        Date time = customerOrder.getOrderTime();
        int oldId = customerOrder.getInvoiceId() != null ? customerOrder.getInvoiceId() : customerOrder.getId();

        customerOrderDAO.delete(customerOrder.getId());
        customerOrder.getCustomerOrderDetails().stream()
                .forEach(e -> e.setCustomerOrderDetailProductStepUps(new ArrayList<>()));
        customerOrder.setOrderTime(time);
        customerOrder.setInvoiceId(oldId);
        CustomerOrder save = save(customerOrder);

        return save;
    }

    @Override
    public List<CustomerOrder> findAllByOrderTimeBetween(Date from, Date to) {
        return customerOrderDAO.findAllByOrderTimeBetween(from, to);
    }

    @Override
    public List<CustomerOrder> findAllCustomerOrderByProductStepUpId(int id) {
        return customerOrderDAO.findAllCustomerOrderByProductStepUpId(id);
    }

    @Override
    public List<CustomerOrder> findAllCustomerOrderByProductId(int id) {
        return customerOrderDAO.findAllCustomerOrderByProductId(id);
    }

    @Override
    @Transactional
    public void delete(int id) {

        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
                AccountTransactionType.CUSTOMER_ORDER);
        if (accountTransaction != null)
            accountTransactionService.delete(accountTransaction.getId());

        CustomerOrder customerOrder = customerOrderDAO.findOne(id);

        customerOrder.getCustomerOrderDetails().forEach(e -> {
            productStockService.stepUp(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());

        });

        customerOrderDAO.delete(id);
    }

    @Override
    public List<CustomerOrder> findAllByCustomerId(int id) {
        return customerOrderDAO.findAllByCustomerId(id);
    }

}
