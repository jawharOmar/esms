package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;

public interface AccountTransactionService {

	List<AccountTransaction> findAllByAccountId(int id);

	AccountTransaction findAccountTransaction(int reference, AccountTransactionType accountTransactionType);

	void delete(int id);

	List<AccountTransaction> findAllByTimeBetweenAndAccountId(Date from, Date to, int id);

	AccountTransaction makeTransaction(AccountTransactionType accountTransactionType, int reference, Double amount);

}
