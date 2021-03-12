package com.joh.esms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.OrderProductStepUpDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.OrderProductStepUp;
import com.joh.esms.model.Product;
import com.joh.esms.model.ProductStepUp;
import com.joh.esms.model.Stock;

@Service
public class OrderProductStepUpServiceImpl implements OrderProductStepUpService {

	private static final Logger logger = Logger.getLogger(OrderProductStepUpServiceImpl.class);

	@Autowired
	private OrderProductStepUpDAO orderProductStepUpDAO;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Autowired
	private ProductStockService productStockService;

	@Override
	@Transactional
	public OrderProductStepUp save(OrderProductStepUp orderProductStepUp) {

		orderProductStepUp = orderProductStepUpDAO.save(orderProductStepUp);

		accountTransactionService.makeTransaction(AccountTransactionType.ORDER, orderProductStepUp.getId(),
				orderProductStepUp.getTotalPayment());

		orderProductStepUp.getProductStepUps().forEach(e -> {
			productStockService.stepUp(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
		});

		return orderProductStepUp;
	}

	@Override
	public List<OrderProductStepUp> findAllByOrderTimeBetween(Date from, Date to) {
		return orderProductStepUpDAO.findAllByOrderTimeBetween(from, to);
	}

	@Override
	public OrderProductStepUp findOne(int id) {
		OrderProductStepUp orderProductStepUp = orderProductStepUpDAO.findOne(id);
		Hibernate.initialize(orderProductStepUp.getProductStepUps());
		orderProductStepUp.getProductStepUps().forEach(e -> {
			Hibernate.initialize(e.getProduct());
		});
		return orderProductStepUp;
	}

	@Override
	@Transactional
	public OrderProductStepUp update(OrderProductStepUp orderProductStepUp) {
		OrderProductStepUp savedOrderProductStepUp = orderProductStepUpDAO.findOne(orderProductStepUp.getId());

		List<ProductStepUp> oldProductStepUp = new ArrayList<>();

		savedOrderProductStepUp.getProductStepUps().forEach(e -> {
			ProductStepUp productStepUp = new ProductStepUp();

			productStepUp.setStock(new Stock(e.getStock().getId()));
			productStepUp.setProduct(new Product(e.getProduct().getId()));
			productStepUp.setQuantity(e.getQuantity());
			oldProductStepUp.add(productStepUp);

		});

		orderProductStepUp = save(orderProductStepUp);

		oldProductStepUp.forEach(e -> {
			productStockService.stepDown(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
		});

		return orderProductStepUp;
	}

	@Override
	@Transactional
	public void delete(int id) {

		AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
				AccountTransactionType.ORDER);
		if (accountTransaction != null)
			accountTransactionService.delete(accountTransaction.getId());

		OrderProductStepUp orderProductStepUp = orderProductStepUpDAO.findOne(id);

		orderProductStepUp.getProductStepUps().forEach(e -> {
			productStockService.stepDown(e.getStock().getId(), e.getProduct().getId(), e.getQuantity());
		});

		orderProductStepUpDAO.delete(id);
	}

	@Override
	public List<OrderProductStepUp> findAllByProductStepUpsProductCode(String code) {
		return orderProductStepUpDAO.findAllByProductStepUpsProductCode(code);
	}

	@Override
	public List<OrderProductStepUp> findAllByVendorId(int id) {
		return orderProductStepUpDAO.findAllByVendorId(id);
	}

}
