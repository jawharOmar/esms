package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.ExpenseCategory;
import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Expense;

public interface ExpenseDAO extends CrudRepository<Expense, Integer> {
	List<Expense> findAllByTimeBetween(Date from, Date to);
    List<Expense> findAllByTimeBetweenAndExpenseCategory(Date from, Date to,ExpenseCategory expenseCategory);
}
