package com.joh.esms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ProductUnitTypeDAO;
import com.joh.esms.model.ProductUnitType;

@Service
public class ProductUnitTypeServiceImpl implements ProductUnitTypeService {

	@Autowired
	private ProductUnitTypeDAO productUnitTypeDAO;

	@Override
	public Iterable<ProductUnitType> findAll() {
		return productUnitTypeDAO.findAll();
	}
	
	@Override
	public ProductUnitType save(ProductUnitType productUnitType) {
		return productUnitTypeDAO.save(productUnitType);
	}

}
