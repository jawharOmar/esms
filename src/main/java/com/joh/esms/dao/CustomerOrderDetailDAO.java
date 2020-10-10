package com.joh.esms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.CustomerOrderDetail;

public interface CustomerOrderDetailDAO extends CrudRepository<CustomerOrderDetail, Integer> {
	List<CustomerOrderDetail> findAllByProductId(int id);

	CustomerOrderDetail findTopByCustomerOrderCustomerIdAndProductIdOrderByIdDesc(int customerId, int productId);

}
