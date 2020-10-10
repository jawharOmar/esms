package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.OrderProductStepUp;

public interface OrderProductStepUpService {

	OrderProductStepUp save(OrderProductStepUp orderProductStepUp);

	List<OrderProductStepUp> findAllByOrderTimeBetween(Date from, Date to);

	OrderProductStepUp findOne(int id);

	OrderProductStepUp update(OrderProductStepUp orderProductStepUp);

	void delete(int id);

	List<OrderProductStepUp> findAllByProductStepUpsProductCode(String code);

	List<OrderProductStepUp> findAllByVendorId(int id);

}
