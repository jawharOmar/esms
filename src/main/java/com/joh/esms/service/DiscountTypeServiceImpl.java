package com.joh.esms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.DiscountTypeDAO;
import com.joh.esms.model.DiscountType;

@Service
public class DiscountTypeServiceImpl implements DiscountTypeService {

	@Autowired
	private DiscountTypeDAO discountTypeDAO;

	@Override
	public Iterable<DiscountType> findAll() {
		return discountTypeDAO.findAll();
	}

}
