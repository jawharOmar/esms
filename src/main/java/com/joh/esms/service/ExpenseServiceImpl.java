package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.ExpenseDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.Expense;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseDAO expenseDAO;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Override
	public Iterable<Expense> findAll(Date from, Date to) {
		return expenseDAO.findAllByTimeBetween(from, to);
	}

	@Override
	@Transactional
	public Expense save(Expense expense) {
		if (expense.getExpenseCategory() != null && expense.getExpenseCategory().getId() == 0)
			expense.setExpenseCategory(null);
		expense = expenseDAO.save(expense);

		accountTransactionService.makeTransaction(AccountTransactionType.EXPENSE, expense.getId(), expense.getAmount());

		return expense;

	}

	@Override
	@Transactional
	public void delete(int id) {

		AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,
				AccountTransactionType.EXPENSE);
		if (accountTransaction != null)
			accountTransactionService.delete(accountTransaction.getId());

		expenseDAO.delete(id);
	}

	@Override
	public Expense findOne(int id) {
		return expenseDAO.findOne(id);
	}

    @Override
    public List<Expense> findbycat(Date from, Date to, int cat) {
        return expenseDAO.findAllByTimeBetweenAndExpenseCategory(from,to,expenseCategoryService.findOne(cat));
    }

	@Override
	@Transactional
	public Expense update(Expense expense) {
		if (expenseDAO.findOne(expense.getId()) == null)
			throw new EntityNotFoundException("Expense not fould with id=" + expense.getId());

		AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(expense.getId(),
				AccountTransactionType.EXPENSE);
		if (accountTransaction != null)
			accountTransactionService.delete(accountTransaction.getId());

		if (expense.getExpenseCategory() != null && expense.getExpenseCategory().getId() == 0)
			expense.setExpenseCategory(null);

		return save(expense);
	}
}
