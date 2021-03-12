package com.joh.esms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.ProductPriceCategory;

public interface ProductPriceCategoryDAO extends CrudRepository<ProductPriceCategory, Integer> {

	@Query("SELECT p FROM ProductPriceCategory p JOIN p.priceCategory WHERE p.porductId IN (?1)")
	List<ProductPriceCategory> findAllByProductIds(Iterable<Integer> ids);

    List<ProductPriceCategory> findAllByPorductId(Integer productId);
}
