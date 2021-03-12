package com.joh.esms.service;

import com.joh.esms.model.Role;

public interface RoleService {

	Iterable<Role> findAll();

	Role save(Role role);

	Role findByName(String name);

}
