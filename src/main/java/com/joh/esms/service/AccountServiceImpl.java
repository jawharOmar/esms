package com.joh.esms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.AccountDAO;
import com.joh.esms.model.Account;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;

	@Override
	public Iterable<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public Account save(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public Account findOne(int id) {
		return accountDAO.findOne(id);
	}

	@Override
	public Account update(Account account) {
		accountDAO.findOne(account.getId());

		return accountDAO.save(account);
	}
}
