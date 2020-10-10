package com.joh.esms.service;

import com.joh.esms.model.Account;

public interface AccountService {

	Iterable<Account> findAll();

	Account save(Account account);

	Account update(Account account);

	Account findOne(int id);
}
