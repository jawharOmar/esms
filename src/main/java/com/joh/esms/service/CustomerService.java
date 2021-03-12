package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.domain.model.CustomerInvoiceD;
import com.joh.esms.domain.model.SearchD;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerPayment;
import com.joh.esms.model.CustomerProject;

public interface CustomerService {

	Iterable<Customer> findAll();

	Customer save(Customer customer);

	void delete(int id);

	Customer findOne(int id);

	Customer update(Customer customer);

	void addCustomerPorject(int id, CustomerProject customerProject);

	void addCustomerPayment(int id, CustomerPayment customerPayment);

	List<CustomerInvoiceD> findAllCustomerInvoice(int id, Date from, Date to);

	Double getCustomerTotalLoan(int id);

	Double getCustomerTotalLoanByTime(int id,Date date);

	//	Search For Customers
	List<SearchD> findCustomersByNameOrPhone(String keyword);

}
