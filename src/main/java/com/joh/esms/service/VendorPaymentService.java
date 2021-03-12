package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.VendorPayment;

public interface VendorPaymentService {

	VendorPayment update(VendorPayment vendorPayment);

	VendorPayment findOne(int id);

	void delete(int id);

	VendorPayment save(VendorPayment vendorPayment);

	List<VendorPayment> findAllByTimeBetween(Date from, Date to);

	List<VendorPayment> findAllByVendorId(int id);

}
