package com.joh.esms.service;

import com.joh.esms.dao.WithdrawCategoryDAO;
import com.joh.esms.model.WithdrawCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class WithdrawCategoryServiceImpl implements WithdrawCategoryService {
    @Autowired
    private WithdrawCategoryDAO withdrawCategoryDAO;

    @Override
    public WithdrawCategory save(WithdrawCategory withdrawCategory) {
        return withdrawCategoryDAO.save(withdrawCategory);
    }

    @Override
    public WithdrawCategory update(WithdrawCategory withdrawCategory) {

        if (withdrawCategoryDAO.findOne(withdrawCategory.getId()) == null) {
            throw new EntityNotFoundException("WithdrawCategory not found with id=" + withdrawCategory.getId());
        }
        return withdrawCategoryDAO.save(withdrawCategory);
    }

    @Override
    public WithdrawCategory findOne(int id) {
        return withdrawCategoryDAO.findOne(id);
    }

    @Override
    public void delete(int id) {
        withdrawCategoryDAO.delete(id);
    }

    @Override
    public Iterable<WithdrawCategory> findAll() {
        return withdrawCategoryDAO.findAll();
    }
}
