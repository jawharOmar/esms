package com.joh.esms.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.ProductStepUpDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerOrderReturnDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.CustomerOrderReturn;
import com.joh.esms.model.OrderProductStepUp;

@Service
public class CustomerOrderReturnServiceImpl implements CustomerOrderReturnService {

    private static final Logger logger = Logger.getLogger(CustomerOrderReturnServiceImpl.class);

    @Autowired
    private CustomerOrderReturnDAO customerOrderReturnDAO;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Autowired
    private ProductStepUpDAO productStepUpService;

    @Autowired
    private ProductStepUpService pstep;

    @Autowired
    private ProductStockService productStockService;

    @Override
    @Transactional
    public CustomerOrderReturn save(CustomerOrderReturn customerOrderReturn) {

        customerOrderReturn.getProductStepUps().forEach(productStepUp -> {
            int id = productStepUpService.findTopByProductIdOrderByIdDesc(productStepUp.getProduct().getId()).getId();
            double price = pstep.findLastPrice(id);
            productStepUp.setPaymentAmount(price * productStepUp.getQuantity());
        });
        customerOrderReturn = customerOrderReturnDAO.save(customerOrderReturn);

        if (accountTransactionService.findAccountTransaction(customerOrderReturn.getId(), AccountTransactionType.CUSTOMER_ORDER_RETURN) != null)
            accountTransactionService.makeTransaction(AccountTransactionType.CUSTOMER_ORDER_RETURN,
                    customerOrderReturn.getId(), customerOrderReturn.getTotalpaid());
        else {
            AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(customerOrderReturn.getId(),
                    AccountTransactionType.CUSTOMER_ORDER_RETURN);
            if (accountTransaction != null)
                accountTransactionService.delete(accountTransaction.getId());

            accountTransactionService.makeTransaction(AccountTransactionType.CUSTOMER_ORDER_RETURN,
                    customerOrderReturn.getId(), customerOrderReturn.getTotalpaid());
        }

        customerOrderReturn.getProductStepUps().forEach(e -> {
            productStockService.stepUp(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
        });

        return customerOrderReturn;
    }

    @Override
    public List<CustomerOrderReturn> findAllByTimeBetween(Date from, Date to) {
        return customerOrderReturnDAO.findAllByTimeBetween(from, to);
    }

    @Override
    public CustomerOrderReturn findOne(int id) {
        return customerOrderReturnDAO.findOne(id);
    }

    @Override
    @Transactional
    public void delete(int id) {

        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
                AccountTransactionType.CUSTOMER_ORDER_RETURN);
        if (accountTransaction != null)
            accountTransactionService.delete(accountTransaction.getId());

        CustomerOrderReturn customerOrderReturn = customerOrderReturnDAO.findOne(id);

        customerOrderReturn.getProductStepUps().forEach(e -> {
            productStockService.stepDown(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
        });


        customerOrderReturnDAO.delete(id);
    }

    @Override
    @Transactional
    public CustomerOrderReturn update(CustomerOrderReturn customerOrderReturn) {
        delete(customerOrderReturn.getId());
        customerOrderReturn = save(customerOrderReturn);
        return customerOrderReturn;
    }

    @Override
    public List<CustomerOrderReturn> findAllByProductStepUpsProductCode(String code) {
        return customerOrderReturnDAO.findAllByProductStepUpsProductCode(code);
    }

}
