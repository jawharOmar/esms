package com.joh.esms.dao;

import com.joh.esms.model.store_list;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ListDAO extends CrudRepository<store_list, Integer> {
	List<store_list> findAll();
}
