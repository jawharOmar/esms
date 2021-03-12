package com.joh.esms.service;

import com.joh.esms.model.ExpenseCategory;

public interface ExpenseCategoryService {

	ExpenseCategory update(ExpenseCategory expenseCategory);

	ExpenseCategory findOne(int id);

	void delete(int id);

	ExpenseCategory save(ExpenseCategory expenseCategory);

	Iterable<ExpenseCategory> findAll();

}
