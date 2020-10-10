package com.joh.esms.service;

import com.joh.esms.model.ProductUnitType;

public interface ProductUnitTypeService {

	Iterable<ProductUnitType> findAll();

	ProductUnitType save(ProductUnitType productUnitType);

}
