package com.joh.esms.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.IncomeDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.Income;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeDAO incomeDAO;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Override
	public Iterable<Income> findAllByTime(Date from, Date to) {
		return incomeDAO.findAllByTimeBetween(from, to);
	}

	@Override
	@Transactional
	public Income save(Income income) {
		if (income.getIncomeCategory() != null && income.getIncomeCategory().getId() == 0)
			income.setIncomeCategory(null);
		income = incomeDAO.save(income);

		accountTransactionService.makeTransaction(AccountTransactionType.INCOME, income.getId(), income.getAmount());
		return income;
	}

	@Override
	@Transactional
	public void delete(int id) {
		AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
				AccountTransactionType.INCOME);
		if (accountTransaction != null)
			accountTransactionService.delete(accountTransaction.getId());

		incomeDAO.delete(id);
	}

	@Override
	public Income findOne(int id) {
		return incomeDAO.findOne(id);
	}

	@Override
	@Transactional
	public Income update(Income income) {
		if (incomeDAO.findOne(income.getId()) == null)
			throw new EntityNotFoundException("Income not fould with id=" + income.getId());

		if (income.getIncomeCategory() != null && income.getIncomeCategory().getId() == 0)
			income.setIncomeCategory(null);

		return save(income);
	}
}
