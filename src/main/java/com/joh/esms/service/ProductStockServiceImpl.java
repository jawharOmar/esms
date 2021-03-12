package com.joh.esms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ProductStockDAO;
import com.joh.esms.exception.ItemNotAvaiableException;
import com.joh.esms.model.Product;
import com.joh.esms.model.ProductStock;
import com.joh.esms.model.Stock;

@Service
public class ProductStockServiceImpl implements ProductStockService {

	@Autowired
	private ProductStockDAO productStockDAO;

	@Autowired
	private ProductService productService;

	@Transactional
	@Override
	public void stepUp(int stockId, int productId, double amount) {
		ProductStock productStock = productStockDAO.findByStockIdAndProductId(stockId, productId);

		if (productStock == null) {
			productStock = new ProductStock();
			productStock.setAmount(0.0);

			Stock stock = new Stock();
			stock.setId(stockId);

			Product product = new Product();
			product.setId(productId);

			productStock.setProduct(product);
			productStock.setStock(stock);

			productStockDAO.save(productStock);

		}

		productStock.setAmount(productStock.getAmount() + amount);

		productStockDAO.save(productStock);

	}

	@Transactional
	@Override
	public void stepDown(int stockId, int productId, double amount) {
		ProductStock productStock = productStockDAO.findByStockIdAndProductId(stockId, productId);

		if (productStock.getAmount() - amount < 0) {
			String message = String.format("This product (%s) is not avaiable enough in the stock",
					productService.findOne(productId).getName());
			throw new ItemNotAvaiableException(message);
		}

		productStock.setAmount(productStock.getAmount() - amount);
		productStockDAO.save(productStock);
	}

	@Override
	public ProductStock findOne(int stockId, int productId) {
		return productStockDAO.findByStockIdAndProductId(stockId, productId);
	}
}
