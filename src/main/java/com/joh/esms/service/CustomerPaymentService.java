package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.CustomerPayment;

public interface CustomerPaymentService {

	Iterable<CustomerPayment> findAll();

	CustomerPayment save(CustomerPayment customerPayment);

	void delete(int id);

	CustomerPayment findOne(int id);

	CustomerPayment update(CustomerPayment customerPayment);

	List<CustomerPayment> findAllByCustomerId(int id);

	Iterable<CustomerPayment> findAllByTimeBetween(Date from, Date to);

	List<CustomerPayment> findAllByCustomerIdAndTimeBetween(int id,Date from, Date to);

}
