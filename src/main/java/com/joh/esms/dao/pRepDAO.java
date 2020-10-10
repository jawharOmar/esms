package com.joh.esms.dao;

import com.joh.esms.model.product_reports;
import com.joh.esms.model.reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface pRepDAO extends JpaRepository<product_reports, Integer> {

	List<product_reports> findAllByproductOrderByTime(int id);


	@Query(value = "from product_reports r where r.product=?1 AND (r.stock=?2 or r.stockname=?3) ORDER BY time")
	List<product_reports> findthem(int p_id,int stock,String stockname);


}
