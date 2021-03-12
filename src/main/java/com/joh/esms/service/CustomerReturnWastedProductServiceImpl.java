package com.joh.esms.service;

import com.joh.esms.dao.CustomerReturnWastedProductDAO;
import com.joh.esms.dao.WastedProductsDAO;
import com.joh.esms.domain.model.WastedProductsD;
import com.joh.esms.model.CustomerReturnWastedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CustomerReturnWastedProductServiceImpl implements CustomerReturnWastedProductService {
    @Autowired
    private CustomerReturnWastedProductDAO wastedProductDAO;

    @Autowired
    private WastedProductsDAO productsDAO;


    @Override
    public void delete(int id) {
        wastedProductDAO.delete(id);
    }

    @Override
    public List<CustomerReturnWastedProduct> findAllByReturnDateBetween(Date from, Date to) {
        return wastedProductDAO.findAllByReturnDateBetween(from,to);
    }

    @Override
    public CustomerReturnWastedProduct findOne(int id) {
        return wastedProductDAO.findOne(id);
    }

    @Override
    public CustomerReturnWastedProduct save(CustomerReturnWastedProduct customerReturnWastedProduct) {
        return wastedProductDAO.save(customerReturnWastedProduct);
    }

    @Transactional
    @Override
    public CustomerReturnWastedProduct update(CustomerReturnWastedProduct customerReturnWastedProduct) {
        delete(customerReturnWastedProduct.getId());
        return wastedProductDAO.save(customerReturnWastedProduct);
    }

    @Override
    public List<WastedProductsD> findAllWastedProduct() {
        return productsDAO.findAllWastedProducts();
    }
}
