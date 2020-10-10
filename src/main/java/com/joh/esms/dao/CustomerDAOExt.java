package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import com.joh.esms.domain.model.CustomerInvoiceD;
import com.joh.esms.domain.model.SearchD;

public interface CustomerDAOExt {

	List<CustomerInvoiceD> findAllCustomerInvoice(int id, Date from, Date to);

	List<SearchD> findCustomersByNameOrPhone(String keyword);

}
