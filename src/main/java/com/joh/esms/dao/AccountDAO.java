package com.joh.esms.dao;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.model.Account;

public interface AccountDAO extends CrudRepository<Account, Integer> {

}
