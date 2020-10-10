package com.joh.esms.dao;

import com.joh.esms.model.reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepDAO extends JpaRepository<reports, Integer> {

	List<reports> findAllByIdOrderByTime(int id);
	List<reports> findAllByIdAndTypeOrderByTime(int id,String type);

}
