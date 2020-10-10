package com.joh.esms.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Vendor;

public interface VendorDAO extends CrudRepository<Vendor, Integer> {
	
	@Query(value = "SELECT ROUND(IFNULL((SELECT SUM(IFNULL(TOTAL_PRICE,0)-IFNULL(TOTAL_PAYMENT,0) -IFNULL(O.DISCOUNT,0) ) FROM ORDER_PRODUCT_STEPUPS O\n" +
			"WHERE I_VENDOR=?1 ),0)-IFNULL((SELECT SUM(TOTAL_PAYMENT)\n" +
			"+ SUM(IF(vp.DISCOUNT IS NULL,0,vp.DISCOUNT)) FROM VENDOR_PAYMENTS vp WHERE I_VENDOR=?1 ),0)\n" +
			" -IFNULL((SELECT SUM(AMOUNT) FROM VENDERRETURNS WHERE I_VENDOR=?1),0) ,3) TOTAL_LOAN;  ", nativeQuery = true)
	Double totalVendorLoan(int id);
}
