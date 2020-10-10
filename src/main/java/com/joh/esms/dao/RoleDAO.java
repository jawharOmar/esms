package com.joh.esms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joh.esms.model.Role;

public interface RoleDAO extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
