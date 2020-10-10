package com.joh.esms.service;

import com.joh.esms.model.VenderReturns;

import java.util.Date;
import java.util.List;


public interface VenderReturnService {

    VenderReturns findone(int id);
    VenderReturns save(VenderReturns customerOrder);
    List<VenderReturns> findAllByTimeBetween(Date from, Date to);
    void delete(int id);
}
