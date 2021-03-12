package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.StockTransfer;

public interface StockTransferDAO extends CrudRepository<StockTransfer, Integer> {
	List<StockTransfer> findAllByTimeBetween(Date from, Date to);
}
