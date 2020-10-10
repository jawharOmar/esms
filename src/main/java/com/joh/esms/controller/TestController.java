package com.joh.esms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joh.esms.dao.ProductDAO;
import com.joh.esms.domain.model.ProductTransactionD;

@Controller
public class TestController {

	@Autowired
	ProductDAO productDAO;

	@GetMapping(path = "/test")
	@ResponseBody
	public List<ProductTransactionD> test() throws IOException {
		System.out.println(productDAO.findAll());
		return productDAO.findProductTransactionDetailByStockId(32, 1);
	}
}
