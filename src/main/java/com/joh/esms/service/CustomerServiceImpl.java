package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.joh.esms.domain.model.SearchD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerDAO;
import com.joh.esms.dao.CustomerOrderDAO;
import com.joh.esms.dao.CustomerPaymentDAO;
import com.joh.esms.dao.CustomerProjectDAO;
import com.joh.esms.domain.model.CustomerInvoiceD;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerPayment;
import com.joh.esms.model.CustomerProject;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerProjectDAO customerProjectDAO;

    @Autowired
    private CustomerPaymentDAO customerPaymentDAO;

    @Override
    public Iterable<Customer> findAll() {
        Iterable<Customer> customers = customerDAO.findAll();

        customers.forEach(e -> {
            e.setTotalLoan(customerDAO.totalLoan(e.getId()));
        });

        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    public void delete(int id) {
        customerDAO.delete(id);
    }

    @Override
    public Customer findOne(int id) {
        Customer customer = customerDAO.findOne(id);
        customer.setTotalLoan(customerDAO.totalLoan(id));
        return customer;
    }

    @Override
    public List<CustomerInvoiceD> findAllCustomerInvoice(int id, Date from, Date to) {
        return customerDAO.findAllCustomerInvoice(id, from, to);
    }

    @Override
    public Customer update(Customer customer) {
        if (customerDAO.findOne(customer.getId()) == null)
            throw new EntityNotFoundException("Customer not found with id=" + customer.getId());

        return customerDAO.save(customer);
    }

    @Override
    @Transactional
    public void addCustomerPorject(int id, CustomerProject customerProject) {
        customerProjectDAO.save(customerProject);
        Customer customer = customerDAO.findOne(id);
        customer.getCustomerProjects().add(customerProject);
        customerDAO.save(customer);
    }

    @Override
    @Transactional
    public void addCustomerPayment(int id, CustomerPayment customerPayment) {
        Customer customer = customerDAO.findOne(id);
        if (customer == null)
            throw new EntityNotFoundException("customer not found with id=" + id);
        customerPayment.setCustomer(customer);
        customerPaymentDAO.save(customerPayment);
    }

    @Override
    public Double getCustomerTotalLoan(int id) {
        return customerDAO.totalLoan(id);
    }

    @Override
    public Double getCustomerTotalLoanByTime(int id, Date date) {
        return customerDAO.totalLoanByTime(id,date);
    }

    @Override
    public List<SearchD> findCustomersByNameOrPhone(String keyword) {
		return customerDAO.findCustomersByNameOrPhone(keyword);
    }
}
