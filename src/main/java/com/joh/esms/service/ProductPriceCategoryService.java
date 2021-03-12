package com.joh.esms.service;

import java.util.List;

import com.joh.esms.model.ProductPriceCategory;

public interface ProductPriceCategoryService {

	List<ProductPriceCategory> findAllByProductIds(Iterable<Integer> ids);

    List<ProductPriceCategory> findAllByPorductId(Integer productId);
}
