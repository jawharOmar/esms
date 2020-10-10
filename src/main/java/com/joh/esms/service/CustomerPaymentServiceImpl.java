package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerPaymentDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.CustomerPayment;

@Service
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

    @Autowired
    private CustomerPaymentDAO customerPaymentDAO;

    @Autowired
    private AccountTransactionService accountTransactionService;


    @Override
    public Iterable<CustomerPayment> findAllByTimeBetween(Date from, Date to) {
        return customerPaymentDAO.findAllByTimeBetween(from, to);
    }

    @Override
    public List<CustomerPayment> findAllByCustomerIdAndTimeBetween(int id, Date from, Date to) {
        return customerPaymentDAO.findAllByCustomerIdAndTimeBetween(id,from,to);
    }

    @Override
    public Iterable<CustomerPayment> findAll() {
        return customerPaymentDAO.findAll();
    }

    @Override
    @Transactional
    public CustomerPayment save(CustomerPayment customerPayment) {
        customerPayment = customerPaymentDAO.save(customerPayment);

        accountTransactionService.makeTransaction(AccountTransactionType.CUSTOMER_PAYMENT, customerPayment.getId(),
                customerPayment.getTotalPayment());


        return customerPayment;
    }

    @Override
    @Transactional
    public void delete(int id) {
        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
                AccountTransactionType.CUSTOMER_PAYMENT);
        if (accountTransaction != null)
            accountTransactionService.delete(accountTransaction.getId());

        customerPaymentDAO.delete(id);
    }

    @Override
    public CustomerPayment findOne(int id) {
        return customerPaymentDAO.findOne(id);
    }

    @Override
    @Transactional
    public CustomerPayment update(CustomerPayment customerPayment) {
        if (customerPaymentDAO.findOne(customerPayment.getId()) == null)
            throw new EntityNotFoundException("Customer Payment not found with id=" + customerPayment.getId());

        AccountTransaction accountTransaction = accountTransactionService
                .findAccountTransaction(customerPayment.getId(), AccountTransactionType.CUSTOMER_PAYMENT);
        if (accountTransaction != null) {
            System.out.println(customerPayment.getId());
            accountTransactionService.delete(accountTransactionService
                    .findAccountTransaction(customerPayment.getId(), AccountTransactionType.CUSTOMER_PAYMENT).getId());
        }


        return save(customerPayment);
    }

    @Override
    public List<CustomerPayment> findAllByCustomerId(int id) {
        return customerPaymentDAO.findAllByCustomerId(id);
    }
}
