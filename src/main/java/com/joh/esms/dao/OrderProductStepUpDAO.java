package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.OrderProductStepUp;

public interface OrderProductStepUpDAO extends CrudRepository<OrderProductStepUp, Integer> {

	List<OrderProductStepUp> findAllByOrderTimeBetween(Date from, Date to);

	List<OrderProductStepUp> findAllByProductStepUpsProductCode(String code);

	List<OrderProductStepUp> findAllByVendorId(int id);

}
