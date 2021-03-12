package com.joh.esms.service;

import java.util.Date;
import java.util.List;

import com.joh.esms.model.VendorReturn;


public interface VenderReturnService {

    VendorReturn findone(int id);
    VendorReturn save(VendorReturn customerOrder);
    List<VendorReturn> findAllByTimeBetween(Date from, Date to);
    void delete(int id);
}
