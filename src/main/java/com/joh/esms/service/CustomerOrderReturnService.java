package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.CustomerOrderReturn;

public interface CustomerOrderReturnService {

	CustomerOrderReturn save(CustomerOrderReturn customerOrderReturn);

	List<CustomerOrderReturn> findAllByTimeBetween(Date from, Date to);

	CustomerOrderReturn findOne(int id);

	void delete(int id);

	CustomerOrderReturn update(CustomerOrderReturn customerOrderReturn);

	List<CustomerOrderReturn> findAllByProductStepUpsProductCode(String code);

}
