package com.joh.esms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.joh.esms.controller.CustomerOrderController;
import com.joh.esms.model.CustomerOrder;
import com.joh.esms.model.CustomerOrderDetail;
import com.joh.esms.model.CustomerOrderDetailProductStepUp;

public class CustomerOrderDAOImpl implements CustomerOrderDAOExt {

	private static final Logger logger = Logger.getLogger(CustomerOrderController.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void delete(int id) {

		CustomerOrder customerOrder = em.find(CustomerOrder.class, id);

		if (customerOrder == null)
			throw new EntityNotFoundException("customerOrder not found with id=" + id);

		for (CustomerOrderDetail customerOrderDetail : customerOrder.getCustomerOrderDetails()) {

			logger.info("customerOrderDetail=" + customerOrderDetail);
			List<CustomerOrderDetailProductStepUp> customerOrderDetailProductStepUps = customerOrderDetail
					.getCustomerOrderDetailProductStepUps();
			logger.info("customerOrderDetailProductStepUps=" + customerOrderDetailProductStepUps);

			for (CustomerOrderDetailProductStepUp customerOrderDetailProductStepUp : customerOrderDetailProductStepUps) {
				Query query = em.createNativeQuery(
						"UPDATE  PRODUCT_STEPUPS SET SOLD_QUANTITY=SOLD_QUANTITY-:amount  WHERE I_PRODUCT_STEPUP=:id");
				query.setParameter("id", customerOrderDetailProductStepUp.getProductStepUp().getId());
				query.setParameter("amount", customerOrderDetailProductStepUp.getAmount());
				query.executeUpdate();
			}

			em.remove(customerOrderDetail);

		}
		em.remove(customerOrder);

		logger.info("customerOrder=" + customerOrder);
	}

}
