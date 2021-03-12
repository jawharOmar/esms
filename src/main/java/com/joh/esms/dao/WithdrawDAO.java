package com.joh.esms.dao;

import com.joh.esms.model.Withdraw;
import com.joh.esms.model.WithdrawCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface WithdrawDAO extends CrudRepository<Withdraw,Integer> {
    List<Withdraw> findAllByTimeBetween(Date from,Date to);

    List<Withdraw> findAllByTimeBetweenAndWithdrawCategory(Date from, Date to, WithdrawCategory withdrawCategory);
}
