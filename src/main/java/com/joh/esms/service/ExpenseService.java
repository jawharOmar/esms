package com.joh.esms.service;

import java.util.Date;

import com.joh.esms.model.Expense;

public interface ExpenseService {

	Expense update(Expense expense);

	Expense findOne(int id);

	void delete(int id);

	Expense save(Expense expense);

	Iterable<Expense> findAll(Date from, Date to);
    Iterable<Expense> findbycat(Date from, Date to,int cat);


}
