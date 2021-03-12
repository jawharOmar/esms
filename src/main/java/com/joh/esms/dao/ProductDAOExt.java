package com.joh.esms.dao;

import java.util.List;

import com.joh.esms.domain.model.ProductD;
import com.joh.esms.domain.model.ProductTransactionD;
import com.joh.esms.domain.model.SearchD;
import com.joh.esms.domain.model.SearchInStockD;

public interface ProductDAOExt {

    List<ProductD> findStock();

    ProductD findProductByCode(String productCode);

    ProductD findProductByProductStepUpId(int productStepUpId);

    List<ProductD> findStockByProductCategoryId(int id);

    List<ProductD> findStockByStockId(int stockId);

    List<ProductTransactionD> findProductTransactionDetailByStockId(int productId, int stockId);

    Double findCostByProductCode(String code);

    //	-----------
//	Pagination
    Integer findTotalPageStocks(Integer showPerPage);

    List<ProductD> findStockPaginate(Integer offset, Integer showPerPage);

    List<ProductD> findStockPaginateByStockId(Integer stockId, Integer offset, Integer showPerPage);

//	Search

    List<SearchInStockD> findByProductCode(String keyword);

    List<SearchInStockD> findByProductName(String name);

    List<SearchInStockD> findByProductCategory(String category);

    ProductD findByProductIdAndStockId(Integer productId, Integer stockId);

    ProductD findByProductId(Integer productId);

    List<ProductD> findByProductCategoryAndStockId(Integer categoryId, Integer stockId);

    List<ProductD> findByProductCategoryId(Integer categoryId);

    List<SearchD> findByProductNameOrCode(String keyword);

    List<ProductD> findStockByKeyword(String keyword);

    List<ProductD> findStockByStockIdAndKeyword(Integer id, String keyword);

}
