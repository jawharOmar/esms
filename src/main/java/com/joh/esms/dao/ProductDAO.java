package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Product;

public interface ProductDAO extends CrudRepository<Product, Integer>, ProductDAOExt {

}
