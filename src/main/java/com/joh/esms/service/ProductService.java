package com.joh.esms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.joh.esms.domain.model.*;
import com.joh.esms.model.Product;

public interface ProductService {

    Product save(Product product);

    List<ProductD> findStock();

    void delete(int id) throws IOException;

    Product findOne(int id);

    Product update(Product product);

    ProductD findProductByCode(String code);

    Iterable<Product> findAll();

    ProductD findProductByProductStepUpId(int productStepUpId);

    List<ProductD> findStockByProductCategoryId(int id);

    List<ProductD> findStockByStockId(int stockId);

    List<ProductTransactionD> findProductTransactionDetailByStockId(int productId, int stockId);

    Double findCostByProductCode(String code);

    Integer findTotalPageStocks(Integer showPerPage);

    List<ProductD> findStockPaginate(Integer offset, Integer showPerPage);

    List<ProductD> findStockPaginateByStockId(Integer stockId, Integer offset, Integer showPerPage);

    ByteArrayInputStream stockToExcel(List<ProductD> productDs, Locale local, boolean cost_role, List<StockColumnD> selectedColumn) throws IOException;


    //	Searching
    List<SearchInStockD> findByProductCode(String keyword);

    List<SearchInStockD> findByProductName(String name);

    List<SearchInStockD> findByProductCategory(String category);

    List<SearchD> findByProductNameOrCode(String keyword);

    ProductD findByProductIdAndStockId(Integer productId, Integer stockId);

    ProductD findByProductId(Integer productId);

    List<ProductD> findByProductCategoryAndStockId(Integer categoryId, Integer stockId);

    List<ProductD> findByProductCategoryId(Integer categoryId);

    List<ProductD> findStockByKeyword(String keyword);

    List<ProductD> findStockByStockIdAndKeyword(Integer stockId, String keyword);


//	End Searching
}
