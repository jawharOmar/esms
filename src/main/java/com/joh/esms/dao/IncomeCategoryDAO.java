package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.IncomeCategory;

public interface IncomeCategoryDAO extends CrudRepository<IncomeCategory, Integer> {
}
