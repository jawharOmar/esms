package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.joh.esms.model.CustomerOrder;

public interface CustomerOrderDAO extends CrudRepository<CustomerOrder, Integer>, CustomerOrderDAOExt {
	List<CustomerOrder> findAllByOrderTimeBetween(Date from, Date to);





	@Query(value = "SELECT DISTINCT CO.* FROM CUSTOMER_ORDERS CO\n" + 
			"INNER JOIN CUSTOMER_ORDER_DETAILS COD USING(I_CUSTOMER_ORDER)\n" + 
			"INNER JOIN CUSTOMER_ORDER_DETAIL_PRODUCT_STEPUPS USING (I_CUSTOMER_ORDER_DETAIL)\n" + 
			"WHERE I_PRODUCT_STEPUP=?1 ;", nativeQuery = true)
	List<CustomerOrder> findAllCustomerOrderByProductStepUpId(int id);

	@Query(value = "SELECT DISTINCT CO.* FROM CUSTOMER_ORDERS CO\n"
			+ "INNER JOIN CUSTOMER_ORDER_DETAILS COD USING(I_CUSTOMER_ORDER)\n"
			+ "INNER JOIN PRODUCTS USING (I_PRODUCT)\n" + "WHERE I_PRODUCT=?1 ;", nativeQuery = true)
	List<CustomerOrder> findAllCustomerOrderByProductId(int id);

	List<CustomerOrder> findAllByCustomerId(int id);

	@Transactional
	@Modifying
	@Query("UPDATE CustomerOrder C SET C.id= ?1 WHERE C.id= ?2 ")
	void updateId(int oldId, int newId);

	CustomerOrder findByInvoiceId(int id);

}
