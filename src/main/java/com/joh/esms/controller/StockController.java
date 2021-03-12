package com.joh.esms.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.esms.model.Stock;
import com.joh.esms.service.StockService;

@Controller()
@RequestMapping(path = "/stocks")
public class StockController {

	private static final Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	@GetMapping()
	public String getAllStocks(Model model) {
		logger.info("getAllStocks->fired");

	Iterable<Stock> stocks = stockService.findAll();
		logger.info("stocks=" + stocks);
		model.addAttribute("stocks", stocks);
		
		return "stocks";
	}

	@GetMapping(path = "/add")
	private String getAddingStock(Model model) {
		logger.info("getAddingStock->fired");

		model.addAttribute("stock", new Stock());
		return "stock/addStock";
	}

	@PostMapping(path = "/add")
	private String addStock(@RequestBody @Valid Stock stock, BindingResult result, Model model) {

		logger.info("addStock->fired");
		logger.info("stock=" + stock);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("stock", stock);
			return "stock/addStock";
		} else {

			stockService.save(stock);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteStock(@PathVariable int id) {
		logger.info("deleteStock->fired");
		stockService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingStock(@PathVariable int id, Model model) {
		logger.info("editingStock->fired");
		logger.info("id=" + id);
		Stock stock = stockService.findOne(id);
		logger.info("stock=" + stock);
		model.addAttribute("stock", stock);

		return "stock/editStock";
	}

	@PostMapping(path = "/update")
	private String updateStock(@RequestBody Stock stock, BindingResult result, Model model) {
		logger.info("updateStock->fired");

		logger.info("stock=" + stock);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {

			model.addAttribute("stock", stock);

			return "stock/editStock";
		} else {

			stockService.update(stock);
			return "success";
		}
	}

}