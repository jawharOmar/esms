package com.joh.esms.service;

import java.util.Date;

import com.joh.esms.model.Income;

public interface IncomeService {

	Income update(Income income);

	Income findOne(int id);

	void delete(int id);

	Income save(Income income);

	Iterable<Income> findAllByTime(Date from, Date to);

}
