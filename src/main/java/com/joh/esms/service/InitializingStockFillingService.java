package com.joh.esms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.domain.model.ProductD;
import com.joh.esms.model.OrderProductStepUp;
import com.joh.esms.model.Product;
import com.joh.esms.model.ProductStepUp;
import com.joh.esms.model.Stock;
import com.joh.esms.model.Vendor;

@Service
public class InitializingStockFillingService {

	@Autowired
	private OrderProductStepUpService orderProductStepUpService;

	@Autowired
	private ProductService productService;

	public void sendToStock() {
		OrderProductStepUp orderProductStepUp = new OrderProductStepUp();

		Vendor vendor = new Vendor();
		vendor.setId(1);

		orderProductStepUp.setVendor(vendor);
		orderProductStepUp.setTotalPayment(0.0);
		orderProductStepUp.setDiscount(0.0);

		Iterable<ProductD> productDs = productService.findStock();

		List<ProductStepUp> productStepUps = new ArrayList<ProductStepUp>();

		Double totalPayment = 0.0;

		for (ProductD productD : productDs) {
			ProductStepUp productStepUp = new ProductStepUp();
			Product product = new Product();
			product.setId(productD.getProductId());
			product.setCode(productD.getCode());
			productStepUp.setProduct(product);
			productStepUp.setQuantity(2000.0);

			double total = productD.getPrice() * 2000;
			productStepUp.setPaymentAmount(total);
			totalPayment += total;

			Stock stock = new Stock();
			stock.setId(1);
			productStepUp.setStock(stock);

			productStepUps.add(productStepUp);

		}

		orderProductStepUp.setProductStepUps(productStepUps);

		orderProductStepUp.setTotalPrice(totalPayment);

		orderProductStepUpService.save(orderProductStepUp);
	}

}
