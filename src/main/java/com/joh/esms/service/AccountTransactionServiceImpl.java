package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.AccountTransactionDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.model.Account;
import com.joh.esms.model.AccountTransaction;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    @Autowired
    private AccountTransactionDAO accountTransactionDAO;

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public AccountTransaction makeTransaction(AccountTransactionType accountTransactionType, int reference,
                                              Double amount) {
        Account account = accountService.findOne(1);

        AccountTransaction accountTransaction = findAccountTransaction(reference, accountTransactionType);

        if (accountTransaction == null && amount != null && amount != 0) {
            accountTransaction = new AccountTransaction();
            accountTransaction.setAccount(account);
            accountTransaction.setAccountTransactionType(accountTransactionType);
            accountTransaction.setAmount(amount);
            accountTransaction.setReference(reference);

            accountTransaction = adjustSign(accountTransaction);

            account.setBalance(account.getBalance() + accountTransaction.getAmount());

            if (account.getBalance() < 0) {
                throw new CusDataIntegrityViolationException("Balance will be less that 0 not allowed");
            }
            accountService.update(account);

            accountTransaction = accountTransactionDAO.save(accountTransaction);

        } else if (accountTransaction != null) {

            // Remove the old transaction balance
            account.setBalance(account.getBalance() - accountTransaction.getAmount());

            accountTransaction.setAmount(amount);
            accountTransaction = adjustSign(accountTransaction);

            account.setBalance(account.getBalance() + accountTransaction.getAmount());

            if (account.getBalance() < 0) {
                throw new CusDataIntegrityViolationException("Balance will be less that 0 not allowed");
            }
            accountService.update(account);

            accountTransaction = accountTransactionDAO.save(accountTransaction);
        }

        return accountTransaction;
    }

    @Override
    public List<AccountTransaction> findAllByAccountId(int id) {
        return accountTransactionDAO.findAllByAccountId(id);
    }

    @Override
    public List<AccountTransaction> findAllByTimeBetweenAndAccountId(Date from, Date to, int id) {
        return accountTransactionDAO.findAllByTimeBetweenAndAccountId(from, to, id);
    }

    @Override
    public void delete(int id) {
        AccountTransaction accountTransaction = accountTransactionDAO.findOne(id);

        Account account = accountService.findOne(accountTransaction.getAccount().getId());
        accountTransaction.setAccount(account);

        account.setBalance(account.getBalance() - accountTransaction.getAmount());

        if (account.getBalance() < 0) {
            throw new CusDataIntegrityViolationException("Balance will be less that 0 not allowed");
        }

        accountService.update(account);
        accountTransactionDAO.delete(id);
    }

    @Override
    public AccountTransaction findAccountTransaction(int reference, AccountTransactionType accountTransactionType) {
        // because I used main account that is why I insert 1
        return accountTransactionDAO.findByAccountIdAndReferenceAndAccountTransactionType(1, reference,
                accountTransactionType);
    }

    private AccountTransaction adjustSign(AccountTransaction accountTransaction) {
        switch (accountTransaction.getAccountTransactionType()) {
            case INCOME:
                break;
            case VENDOR_RETURN:
                break;
            case CUSTOMER_ORDER:
                break;
            case CUSTOMER_PAYMENT:
                break;
            case EXPENSE:
                accountTransaction.setAmount(-accountTransaction.getAmount());
                break;
            case ORDER:
                accountTransaction.setAmount(-accountTransaction.getAmount());
                break;
            case VENDOR_PAYMENT:
                accountTransaction.setAmount(-accountTransaction.getAmount());
                break;
            case CUSTOMER_ORDER_RETURN:
                accountTransaction.setAmount(-accountTransaction.getAmount());
                break;
            case WITHDRAW:
                accountTransaction.setAmount(-accountTransaction.getAmount());
                break;
            default:
                throw new CusDataIntegrityViolationException("Account transaction type not implemented");
        }
        return accountTransaction;
    }

}
