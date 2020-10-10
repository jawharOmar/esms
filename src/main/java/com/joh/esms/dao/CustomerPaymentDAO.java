package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.CustomerPayment;

public interface CustomerPaymentDAO extends CrudRepository<CustomerPayment, Integer> {
	List<CustomerPayment> findAllByCustomerId(int id);
	List<CustomerPayment> findAllByTimeBetween(Date from, Date to);

	List<CustomerPayment> findAllByCustomerIdAndTimeBetween(int id,Date from, Date to);
	
}
