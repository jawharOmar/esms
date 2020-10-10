package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Stock;

public interface StockDAO extends CrudRepository<Stock, Integer> {
}
