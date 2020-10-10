package com.joh.esms.dao;

import com.joh.esms.model.vendor_reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepDAO extends JpaRepository<vendor_reports, Integer> {

	List<vendor_reports> findAllByIdOrderByTime(int id);
	List<vendor_reports> findAllByIdAndTypeOrderByTime(int id,String type);

}
