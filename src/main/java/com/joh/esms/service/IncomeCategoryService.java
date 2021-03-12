package com.joh.esms.service;

import com.joh.esms.model.IncomeCategory;

public interface IncomeCategoryService {

	IncomeCategory update(IncomeCategory incomeCategory);

	IncomeCategory findOne(int id);

	IncomeCategory save(IncomeCategory incomeCategory);

	Iterable<IncomeCategory> findAll();

	void delete(int id);

}
