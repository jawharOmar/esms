package com.joh.esms.dao;

import com.joh.esms.model.ExpiredProducts;
import com.joh.esms.model.MinimumProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredDAO extends JpaRepository<ExpiredProducts, Integer> {



}
