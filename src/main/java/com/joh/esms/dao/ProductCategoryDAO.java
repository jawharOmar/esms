package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.ProductCategory;

public interface ProductCategoryDAO extends CrudRepository<ProductCategory, Integer> {

}
