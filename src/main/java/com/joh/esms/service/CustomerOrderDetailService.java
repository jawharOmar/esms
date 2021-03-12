package com.joh.esms.service;

import java.util.List;

import com.joh.esms.model.CustomerOrderDetail;

public interface CustomerOrderDetailService {

	List<CustomerOrderDetail> findAllByProductId(int id);

	CustomerOrderDetail findLastSoldToCustomer(int customerId, int productId);

}
