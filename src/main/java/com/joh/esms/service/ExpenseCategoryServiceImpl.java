package com.joh.esms.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ExpenseCategoryDAO;
import com.joh.esms.model.ExpenseCategory;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

	@Autowired
	private ExpenseCategoryDAO expenseCategoryDAO;

	@Override
	public Iterable<ExpenseCategory> findAll() {
		return expenseCategoryDAO.findAll();
	}

	@Override
	public ExpenseCategory save(ExpenseCategory expenseCategory) {
		return expenseCategoryDAO.save(expenseCategory);
	}

	@Override
	public void delete(int id) {
		expenseCategoryDAO.delete(id);
	}

	@Override
	public ExpenseCategory findOne(int id) {
		return expenseCategoryDAO.findOne(id);
	}

	@Override
	public ExpenseCategory update(ExpenseCategory expenseCategory) {
		if (expenseCategoryDAO.findOne(expenseCategory.getId()) == null)
			throw new EntityNotFoundException("ExpenseCategory not found with id=" + expenseCategory.getId());

		return expenseCategoryDAO.save(expenseCategory);
	}
}
