package com.joh.esms.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.IncomeCategoryDAO;
import com.joh.esms.model.IncomeCategory;

@Service
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

	@Autowired
	private IncomeCategoryDAO incomeCategoryDAO;

	@Override
	public Iterable<IncomeCategory> findAll() {
		return incomeCategoryDAO.findAll();
	}

	@Override
	public IncomeCategory save(IncomeCategory incomeCategory) {
		return incomeCategoryDAO.save(incomeCategory);
	}

	@Override
	public void delete(int id) {
		incomeCategoryDAO.delete(id);
	}

	@Override
	public IncomeCategory findOne(int id) {
		return incomeCategoryDAO.findOne(id);
	}

	@Override
	public IncomeCategory update(IncomeCategory incomeCategory) {
		if (incomeCategoryDAO.findOne(incomeCategory.getId()) == null)
			throw new EntityNotFoundException("IncomeCategory not found with id=" + incomeCategory.getId());

		return incomeCategoryDAO.save(incomeCategory);
	}
}
