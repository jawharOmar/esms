package com.joh.esms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;

public interface AccountTransactionDAO extends CrudRepository<AccountTransaction, Integer> {

	List<AccountTransaction> findAllByAccountId(int id);

	List<AccountTransaction> findAllByTimeBetweenAndAccountId(Date from, Date to, int id);

	AccountTransaction findByAccountIdAndReferenceAndAccountTransactionType(int accountId, int reference,
			AccountTransactionType accountTransactionType);

}
