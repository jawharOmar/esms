package com.joh.esms.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.esms.dao.ProductDAO;
import com.joh.esms.model.Product;

@Controller()
@RequestMapping(path = "/store")
public class OnlineStorController {

	private static final Logger logger = Logger.getLogger(OnlineStorController.class);

	@Autowired
	private ProductDAO prodao;

	@GetMapping()
	private String OnlineStore(Model model) {

		return "store";
	}

	@GetMapping(value = "/setting")
	private String setting(Model model) {
		logger.info("setting->fired");

		return "setting";
	}

	@GetMapping(value = "/list/add")
	private String add(Model model) {

		Iterable<Product> product = prodao.findAll();
		model.addAttribute("product", product);
		return "item-add";
	}

}
