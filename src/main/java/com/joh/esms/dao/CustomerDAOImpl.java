package com.joh.esms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import com.joh.esms.domain.model.SearchD;
import org.apache.log4j.Logger;

import com.joh.esms.controller.CustomerOrderController;
import com.joh.esms.domain.model.CustomerInvoiceD;
import com.joh.esms.domain.model.CustomerInvoiceD.INVOICE_TYPE;

public class CustomerDAOImpl implements CustomerDAOExt {

	private static final Logger logger = Logger.getLogger(CustomerOrderController.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public List<CustomerInvoiceD> findAllCustomerInvoice(int id, Date from, Date to) {

		Query query = em.createNativeQuery("SELECT *\n"
				+ "FROM (SELECT I_CUSTOMER_ORDER AS REFERENCE,0 AS T_TYPE,ROUND(IFNULL(TOTAL_PRICE, 0) - IFNULL(TOTAL_PAYMENT, 0) - IFNULL(DISCOUNT, 0), 3) AMOUNT,ORDER_TIME T_TIME\n"
				+ "FROM CUSTOMER_ORDERS\n" + "WHERE I_CUSTOMER =:I_CUSTOMER\n"
				+ "AND (ORDER_TIME BETWEEN :FROM AND :TO OR :ALL)\n" + "UNION\n"
				+ "SELECT I_CUSTOMER_PAYMENT AS REFERENCE,1 AS T_TYPE,TOTAL_PAYMENT AMOUNT,PAYMENT_TIME T_TIME\n"
				+ "FROM CUSTOMER_PAYMENTS\n" + "WHERE I_CUSTOMER = :I_CUSTOMER\n"
				+ "AND (PAYMENT_TIME BETWEEN :FROM AND :TO OR :ALL)\n"
				+ "UNION SELECT I_CUSTOMER_ORDER_RETURN AS REFERENCE,2 AS T_TYPE,TOTAL_PRICE AMOUNT,CUSTOMER_ORDER_RETURN_TIME T_TIME\n"
				+ "FROM CUSTOMER_ORDER_RETURNS\n" + "WHERE I_CUSTOMER = :I_CUSTOMER\n"
				+ "AND (CUSTOMER_ORDER_RETURN_TIME  BETWEEN :FROM AND :TO OR :ALL)\n" + ") P\n" + "ORDER BY T_TIME;");

		query.setParameter("I_CUSTOMER", id);

		if (from == null && to == null) {
			query.setParameter("ALL", true);
			query.setParameter("FROM", null);
			query.setParameter("TO", null);
		} else {
			query.setParameter("ALL", false);
			query.setParameter("FROM", from, TemporalType.TIMESTAMP);
			query.setParameter("TO", to, TemporalType.TIMESTAMP);
		}

		logger.info("query=" + query);

		List<Object[]> rows = query.getResultList();

		List<CustomerInvoiceD> customerInvoiceDs = new ArrayList<>();
		for (Object[] columns : rows) {
			CustomerInvoiceD customerInvoiceD = new CustomerInvoiceD();

			customerInvoiceD.setReference(Integer.parseInt("" + columns[0]));

			if (Integer.parseInt("" + columns[1]) == 0) {
				customerInvoiceD.setInvoiceType(INVOICE_TYPE.ORDER);
			} else if (Integer.parseInt("" + columns[1]) == 1) {
				customerInvoiceD.setInvoiceType(INVOICE_TYPE.PAYMENT);
			} else {
				customerInvoiceD.setInvoiceType(INVOICE_TYPE.RETURN);
			}
			customerInvoiceD.setAmount((Double) columns[2]);
			customerInvoiceD.setTime((Date) columns[3]);
			customerInvoiceDs.add(customerInvoiceD);
		}
		return customerInvoiceDs;
	}


	@Override
	public List<SearchD> findCustomersByNameOrPhone(String keyword) {
		List<SearchD> searchDS = new ArrayList<>();
		Query query = em.createNativeQuery("SELECT I_CUSTOMER,FULL_NAME,PHONE FROM CUSTOMERS WHERE (FULL_NAME LIKE CONCAT('%',?1,'%') || PHONE LIKE CONCAT('%',?1,'%'))");
		query.setParameter(1,keyword);

		List<Object[]> resultList = query.getResultList();

		for (Object row[] : resultList) {
			SearchD searchD = new SearchD();
			searchD.setId((Integer) row[0]);
			searchD.setKeyword((String) row[1]);
			searchD.setSecondKeyword((String) row[2]);

			searchDS.add(searchD);
		}
		return searchDS;
	}
}
