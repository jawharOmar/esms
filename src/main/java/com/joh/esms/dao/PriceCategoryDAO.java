package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.PriceCategory;

public interface PriceCategoryDAO extends CrudRepository<PriceCategory, Integer> {
}
