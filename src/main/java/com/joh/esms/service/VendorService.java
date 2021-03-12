package com.joh.esms.service;

import com.joh.esms.model.Vendor;

public interface VendorService {

	Vendor save(Vendor vendor);

	Iterable<Vendor> findAll();

	void delete(int id);

	Vendor findOne(int id);

	Vendor update(Vendor vendor);

	Double totalVendorLoan(int id);

}
