package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.VendorReturn;

public interface VendorReturnDAO extends CrudRepository<VendorReturn, Integer> ,VendorReturnDAOExt {

    List<VendorReturn> findAllByTimeBetween(Date from, Date to);

}
