package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Income;

public interface IncomeDAO extends CrudRepository<Income, Integer> {
	List<Income> findAllByTimeBetween(Date from, Date to);
}
