package com.joh.esms.dao;

import com.joh.esms.model.List_photos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface photoDAO extends CrudRepository<List_photos, Integer> {
	List<List_photos> findAll();
}
