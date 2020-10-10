package com.joh.esms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.RoleDAO;
import com.joh.esms.model.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public Iterable<Role> findAll() {
		return roleDAO.findAll();
	}

	@Override
	public Role save(Role role) {
		return roleDAO.saveAndFlush(role);
	}

	@Override
	public Role findByName(String name) {
		return roleDAO.findByName(name);
	}
}
