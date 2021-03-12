package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ProductStepUpDAO;
import com.joh.esms.model.ProductStepUp;

@Service
public class ProductStepUpServiceImpl implements ProductStepUpService {

	@Autowired
	private ProductStepUpDAO productStepUpDAO;

	@Override
	public ProductStepUp save(ProductStepUp productStepUp) {
		return productStepUpDAO.save(productStepUp);
	}

	@Override
	public List<ProductStepUp> findAllByTimeBetween(Date from, Date to) {
		// return productStepUpDAO.findAllByTimeBetween(from, to);
		return null;
	}

	@Override
	public List<ProductStepUp> findAllByExpirationDateLessThanEqual(Date to) {
		return productStepUpDAO.findAllByExpirationDateLessThanEqualOrderByExpirationDate(to);
	}

	@Override
	public void delete(int id) {
		productStepUpDAO.delete(id);
	}

	@Override
	public Double findLastPrice(int id) {
		ProductStepUp productStepUp = productStepUpDAO.findOne(id);
		if (productStepUp == null)
			return 0.0;
		else
			return productStepUp.getPaymentAmount() / productStepUp.getQuantity();
	}
}
