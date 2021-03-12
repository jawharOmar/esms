package com.joh.esms.service;

import com.joh.esms.model.ProductCategory;

public interface ProductCategorySevice {


	Iterable<ProductCategory> findAll();

	ProductCategory save(ProductCategory productCategory);

	void delete(int id);

	ProductCategory findOne(int id);

	ProductCategory update(ProductCategory productCategory);

}
