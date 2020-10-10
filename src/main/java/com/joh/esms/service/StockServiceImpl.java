package com.joh.esms.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.StockDAO;
import com.joh.esms.model.Stock;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockDAO stockDAO;

	@Override
	public Iterable<Stock> findAll() {
		return stockDAO.findAll();
	}
	
	@Override
	public Stock save(Stock stock) {
		return stockDAO.save(stock);
	}

	@Override
	@Transactional
	public void delete(int id) {
		stockDAO.delete(id);
	}

	@Override
	public Stock findOne(int id) {
		return stockDAO.findOne(id);
	}

	@Override
	public Stock update(Stock stock) {

		Stock findOne = stockDAO.findOne(stock.getId());

		if (findOne == null)
			throw new EntityNotFoundException();

		return stockDAO.save(stock);
	}
}
