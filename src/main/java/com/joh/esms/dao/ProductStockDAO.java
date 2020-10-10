package com.joh.esms.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.ProductStock;

import java.util.List;

public interface ProductStockDAO extends CrudRepository<ProductStock, Integer> {
	ProductStock findByStockIdAndProductId(int stockId, int productId);



}
