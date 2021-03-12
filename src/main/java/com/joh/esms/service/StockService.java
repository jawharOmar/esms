package com.joh.esms.service;

import com.joh.esms.model.Stock;

public interface StockService {

	Stock save(Stock stock);

	void delete(int id);

	Stock findOne(int id);

	Stock update(Stock stock);

	Iterable<Stock> findAll();

}
