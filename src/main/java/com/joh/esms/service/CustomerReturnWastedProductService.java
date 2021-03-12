package com.joh.esms.service;

import com.joh.esms.domain.model.WastedProductsD;
import com.joh.esms.model.CustomerReturnWastedProduct;

import java.util.Date;
import java.util.List;

public interface CustomerReturnWastedProductService {

    void delete(int id);

    List<CustomerReturnWastedProduct> findAllByReturnDateBetween(Date from, Date to);

    CustomerReturnWastedProduct findOne(int id);

    CustomerReturnWastedProduct save(CustomerReturnWastedProduct customerReturnWastedProduct);

    CustomerReturnWastedProduct update(CustomerReturnWastedProduct customerReturnWastedProduct);


    List<WastedProductsD> findAllWastedProduct();
}
