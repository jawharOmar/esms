package com.joh.esms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.PriceCategoryDAO;
import com.joh.esms.model.PriceCategory;

@Service
public class PriceCategoryServiceImpl implements PriceCategoryService {

	@Autowired
	private PriceCategoryDAO priceCategoryDAO;

	@Override
	public List<PriceCategory> findAll() {
		return (List<PriceCategory>) priceCategoryDAO.findAll();
	}

	@Override
	public PriceCategory save(PriceCategory priceCategory) {
		return priceCategoryDAO.save(priceCategory);
	}

	@Override
	@Transactional
	public void delete(int id) {
		priceCategoryDAO.delete(id);
	}

	@Override
	public PriceCategory findOne(int id) {
		return priceCategoryDAO.findOne(id);
	}

	@Override
	@Transactional
	public PriceCategory update(PriceCategory priceCategory) {
		priceCategoryDAO.findOne(priceCategory.getId());
		return priceCategoryDAO.save(priceCategory);
	}

}
