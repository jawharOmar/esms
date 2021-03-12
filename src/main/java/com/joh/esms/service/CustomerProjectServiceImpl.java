package com.joh.esms.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.CustomerProjectDAO;
import com.joh.esms.model.CustomerProject;

@Service
public class CustomerProjectServiceImpl implements CustomerProjectService {

	@Autowired
	private CustomerProjectDAO customerProjectDAO;

	@Override
	public CustomerProject save(CustomerProject customerProject) {
		return customerProjectDAO.save(customerProject);
	}

	@Override
	public void delete(int id) {
		customerProjectDAO.delete(id);
	}

	@Override
	public CustomerProject findOne(int id) {
		return customerProjectDAO.findOne(id);
	}

	@Override
	public CustomerProject update(CustomerProject customerProject) {
		if (customerProjectDAO.findOne(customerProject.getId()) == null)
			throw new EntityNotFoundException("Customer not fould with id=" + customerProject.getId());

		return customerProjectDAO.save(customerProject);
	}
}
