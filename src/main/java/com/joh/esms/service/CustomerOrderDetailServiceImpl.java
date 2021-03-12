package com.joh.esms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerOrderDetailDAO;
import com.joh.esms.model.CustomerOrderDetail;

@Service
public class CustomerOrderDetailServiceImpl implements CustomerOrderDetailService {

	@Autowired
	private CustomerOrderDetailDAO customerOrderDetailDAO;

	@Override
	public List<CustomerOrderDetail> findAllByProductId(int id) {
		return customerOrderDetailDAO.findAllByProductId(id);
	}

	@Override
	public CustomerOrderDetail findLastSoldToCustomer(int customerId, int productId) {
		return customerOrderDetailDAO.findTopByCustomerOrderCustomerIdAndProductIdOrderByIdDesc(customerId, productId);

	}

}
