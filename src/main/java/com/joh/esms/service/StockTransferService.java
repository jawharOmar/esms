package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.StockTransfer;

public interface StockTransferService {

	List<StockTransfer> findAll(Date from, Date to);

	StockTransfer save(StockTransfer stockTransfer);

	void delete(int id);

	StockTransfer update(StockTransfer stockTransfer);

	StockTransfer findOne(int id);

}
