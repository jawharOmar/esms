package com.joh.esms.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.model.OrderPreProduct;

public interface OrderPreProductService {

	void delete(int id);

	List<OrderPreProduct> findAllByOrderTimeBetween(Date from, Date to);

	OrderPreProduct findOne(int id);

	OrderPreProduct save(OrderPreProduct orderPreProduct, MultipartFile[] files) throws IOException;

	OrderPreProduct update(OrderPreProduct orderPreProduct, MultipartFile[] files) throws IOException;

}
