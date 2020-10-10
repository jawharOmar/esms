package com.joh.esms.service;

import com.joh.esms.model.ProductStock;

public interface ProductStockService {

	void stepUp(int stockId, int productId, double amount);

	void stepDown(int stockId, int productId, double amount);

	ProductStock findOne(int stockId, int productId);

}
