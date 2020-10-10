package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.VendorPayment;

public interface VendorPaymentDAO extends CrudRepository<VendorPayment, Integer> {
	List<VendorPayment> findAllByTimeBetween(Date from, Date to);

	List<VendorPayment> findAllByVendorId(int id);
}
