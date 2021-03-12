package com.joh.esms.service;

import com.joh.esms.model.Withdraw;

import java.util.Date;

public interface WithdrawService {
    Withdraw save(Withdraw withdraw);

    Withdraw update(Withdraw withdraw);

    Withdraw findOne(int id);

    void delete(int id);

    Iterable<Withdraw> findAll(Date from, Date to);

    Iterable<Withdraw> findByCat(Date from, Date to,int cat);
}
