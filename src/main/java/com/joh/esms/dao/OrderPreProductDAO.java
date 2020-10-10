package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.OrderPreProduct;

public interface OrderPreProductDAO extends CrudRepository<OrderPreProduct, Integer> {
	List<OrderPreProduct> findAllByOrderTimeBetween(Date from, Date to);
}
