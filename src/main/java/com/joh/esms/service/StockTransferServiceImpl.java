package com.joh.esms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.StockTransferDAO;
import com.joh.esms.model.Product;
import com.joh.esms.model.Stock;
import com.joh.esms.model.StockTransfer;
import com.joh.esms.model.StockTransferDetail;

@Service
public class StockTransferServiceImpl implements StockTransferService {

	@Autowired
	private StockTransferDAO stockTransferDAO;

	@Autowired
	private ProductStockService productStockService;

	@Override
	public List<StockTransfer> findAll(Date from, Date to) {
		return stockTransferDAO.findAllByTimeBetween(from, to);
	}

	@Override
	@Transactional
	public StockTransfer save(StockTransfer stockTransfer) {

		stockTransfer.getStockTransferDetails().forEach(e -> {
			productStockService.stepDown(stockTransfer.getFrom().getId(), e.getProduct().getId(), e.getAmount());
			productStockService.stepUp(stockTransfer.getTo().getId(), e.getProduct().getId(), e.getAmount());
		});

		return stockTransferDAO.save(stockTransfer);
	}

	@Override
	@Transactional
	public StockTransfer update(StockTransfer stockTransfer) {

		StockTransfer oldStockTransfer = new StockTransfer();

		StockTransfer savedStockTransfer = stockTransferDAO.findOne(stockTransfer.getId());

		oldStockTransfer.setFrom(new Stock(savedStockTransfer.getFrom().getId()));
		oldStockTransfer.setTo(new Stock(savedStockTransfer.getTo().getId()));

		savedStockTransfer.getStockTransferDetails().forEach(e -> {
			StockTransferDetail stockTransferDetail = new StockTransferDetail();
			stockTransferDetail.setProduct(new Product(e.getProduct().getId()));
			stockTransferDetail.setAmount(e.getAmount());
			oldStockTransfer.getStockTransferDetails().add(stockTransferDetail);
		});

		stockTransfer = save(stockTransfer);

		oldStockTransfer.getStockTransferDetails().forEach(e -> {
			productStockService.stepUp(savedStockTransfer.getFrom().getId(), e.getProduct().getId(), e.getAmount());
			productStockService.stepDown(savedStockTransfer.getTo().getId(), e.getProduct().getId(), e.getAmount());
		});

		return stockTransfer;
	}

	@Override
	@Transactional
	public void delete(int id) {
		StockTransfer savedStockTransfer = stockTransferDAO.findOne(id);
		savedStockTransfer.getStockTransferDetails().forEach(e -> {
			productStockService.stepUp(savedStockTransfer.getFrom().getId(), e.getProduct().getId(), e.getAmount());
			productStockService.stepDown(savedStockTransfer.getTo().getId(), e.getProduct().getId(), e.getAmount());
		});
		stockTransferDAO.delete(id);
	}

	@Override
	public StockTransfer findOne(int id) {
		return stockTransferDAO.findOne(id);
	}

}
