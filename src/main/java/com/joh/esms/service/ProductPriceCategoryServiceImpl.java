package com.joh.esms.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ProductPriceCategoryDAO;
import com.joh.esms.model.ProductPriceCategory;

@Service
public class ProductPriceCategoryServiceImpl implements ProductPriceCategoryService {

	private static final Logger logger = Logger.getLogger(ProductPriceCategoryServiceImpl.class);

	@Autowired
	private ProductPriceCategoryDAO productPriceCategoryDAO;

	@Override
	public List<ProductPriceCategory> findAllByProductIds(Iterable<Integer> ids) {
		logger.debug("ids=" + ids);

		return productPriceCategoryDAO.findAllByProductIds(ids);
	}

	@Override
	public List<ProductPriceCategory> findAllByPorductId(Integer productId) {
		return productPriceCategoryDAO.findAllByPorductId(productId);
	}

}
