package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.CustomerOrderReturn;

public interface CustomerOrderReturnDAO extends CrudRepository<CustomerOrderReturn, Integer> {

	List<CustomerOrderReturn> findAllByTimeBetween(Date from, Date to);

	List<CustomerOrderReturn> findAllByProductStepUpsProductCode(String code);

}
