package com.joh.esms.dao;

import com.joh.esms.model.VenderReturns;
import com.joh.esms.model.VendorPayment;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VendorReturnDAO extends CrudRepository<VenderReturns, Integer> ,VendorReturnDAOExt {

    List<VenderReturns> findAllByTimeBetween(Date from, Date to);

}
