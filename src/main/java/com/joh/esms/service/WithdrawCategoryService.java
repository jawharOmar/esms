package com.joh.esms.service;

import com.joh.esms.model.WithdrawCategory;

public interface WithdrawCategoryService {
    WithdrawCategory save(WithdrawCategory withdrawCategory);

    WithdrawCategory update(WithdrawCategory withdrawCategory);

    WithdrawCategory findOne(int id);

    void delete(int id);

    Iterable<WithdrawCategory> findAll();
}
