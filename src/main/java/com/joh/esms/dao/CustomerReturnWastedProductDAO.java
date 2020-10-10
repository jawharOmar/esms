package com.joh.esms.dao;

import com.joh.esms.model.CustomerReturnWastedProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CustomerReturnWastedProductDAO extends CrudRepository<CustomerReturnWastedProduct,Integer> {

    List<CustomerReturnWastedProduct> findAllByReturnDateBetween(Date from, Date to);

}
