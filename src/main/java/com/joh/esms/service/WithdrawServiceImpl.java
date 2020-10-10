package com.joh.esms.service;

import com.joh.esms.dao.WithdrawCategoryDAO;
import com.joh.esms.dao.WithdrawDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.model.Withdraw;
import com.joh.esms.model.WithdrawCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private WithdrawDAO withdrawDAO;

    @Autowired
    private WithdrawCategoryService withdrawCategoryService;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Override
    @Transactional
    public Withdraw save(Withdraw withdraw) {

        if(withdraw.getWithdrawCategory() !=null && withdraw.getWithdrawCategory().getId()==0){
            withdraw.setWithdrawCategory(null);
        }
        withdraw = withdrawDAO.save(withdraw);
        accountTransactionService.makeTransaction(AccountTransactionType.WITHDRAW,withdraw.getId(),withdraw.getAmount());

        return withdraw;
    }

    @Override
    @Transactional
    public Withdraw update(Withdraw withdraw) {

        if (withdrawDAO.findOne(withdraw.getId()) == null) {
            throw new EntityNotFoundException("Withdraw not found with id=" + withdraw.getId());
        }

        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(withdraw.getId(),
                AccountTransactionType.WITHDRAW);
        if (accountTransaction != null){
            accountTransactionService.delete(accountTransaction.getId());
        }

        if(withdraw.getWithdrawCategory() !=null && withdraw.getWithdrawCategory().getId()==0){
            withdraw.setWithdrawCategory(null);
        }

        return save(withdraw);
    }

    @Override
    public Withdraw findOne(int id) {
        return withdrawDAO.findOne(id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        AccountTransaction accountTransaction = accountTransactionService.findAccountTransaction(id,AccountTransactionType.WITHDRAW);
        if (accountTransaction != null){
            accountTransactionService.delete(accountTransaction.getId());
        }
        withdrawDAO.delete(id);
    }

    @Override
    public Iterable<Withdraw> findAll(Date from, Date to) {
        return withdrawDAO.findAllByTimeBetween(from,to);
    }

    @Override
    public Iterable<Withdraw> findByCat(Date from, Date to, int cat) {
        return withdrawDAO.findAllByTimeBetweenAndWithdrawCategory(from,to,withdrawCategoryService.findOne(cat));
    }
}
