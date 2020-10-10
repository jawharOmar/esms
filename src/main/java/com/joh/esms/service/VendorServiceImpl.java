package com.joh.esms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.VendorDAO;
import com.joh.esms.model.Vendor;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorDAO vendorDAO;

	@Override
	public Iterable<Vendor> findAll() {
		Iterable<Vendor> vendors = vendorDAO.findAll();
		vendors.forEach(e -> {
			e.setTotalLoan(vendorDAO.totalVendorLoan(e.getId()));
		});
		return vendors;
	}

	@Override
	@Transactional
	public Vendor save(Vendor vendor) {
		return vendorDAO.save(vendor);
	}

	@Override
	@Transactional
	public void delete(int id) {
		vendorDAO.delete(id);
	}

	@Override
	public Vendor findOne(int id) {
		Vendor vendor = vendorDAO.findOne(id);
		vendor.setTotalLoan(vendorDAO.totalVendorLoan(vendor.getId()));
		return vendor;
	}

	@Override
	@Transactional
	public Vendor update(Vendor vendor) {
		vendor.setId(vendorDAO.findOne(vendor.getId()).getId());
		return vendorDAO.save(vendor);
	}

	@Override
	public Double totalVendorLoan(int id) {
		return vendorDAO.totalVendorLoan(id);
	}
}
