package com.joh.esms.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.model.Product;
import com.joh.esms.model.Stock;
import com.joh.esms.model.StockTransfer;
import com.joh.esms.service.ProductService;
import com.joh.esms.service.StockService;
import com.joh.esms.service.StockTransferService;

@Controller()
@RequestMapping(path = "/stockTransfers")
public class StockTransferController {

	private static final Logger logger = Logger.getLogger(StockTransferController.class);

	@Autowired
	private StockTransferService stockTransferService;

	@Autowired
	private StockService stockService;

	@Autowired
	private ProductService productService;

	@GetMapping()
	public String getAllStockTransfer(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllStockTransfer->fired");

		List<StockTransfer> stockTransfers = stockTransferService.findAll(from, to);

		model.addAttribute("stockTransfers", stockTransfers);

		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "stockTransfers";
	}

	@GetMapping(path = "/add")
	private String getAddingStockTransfer(Model model) throws JsonProcessingException {
		logger.info("getAddingStockTransfer->fired");

		Iterable<Stock> stocks = stockService.findAll();

		ObjectMapper objectMapper = new ObjectMapper();

		Iterable<Product> products = productService.findAll();
		logger.info("products=" + products);
		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));

		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));

		model.addAttribute("jsonStockTransfer", objectMapper.writeValueAsString(new StockTransfer()));

		return "addStockTransfer";
	}

	@PostMapping(path = "/add")
	private String addStockTransfer(@RequestBody @Validated() StockTransfer stockTransfer, Locale locale) {
		logger.info("addStockTransfer->fired");

		logger.info("stockTransfer=" + stockTransfer);

		stockTransfer = stockTransferService.save(stockTransfer);

		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String getEditingStockTransfer(@PathVariable int id, Model model) throws JsonProcessingException {

		logger.info("getEditingCustomerOrder->fired");

		StockTransfer stockTransfer = stockTransferService.findOne(id);

		logger.info("stockTransfer=" + stockTransfer);

		Iterable<Stock> stocks = stockService.findAll();

		ObjectMapper objectMapper = new ObjectMapper();

		Iterable<Product> products = productService.findAll();

		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));
		model.addAttribute("jsonStockTransfer", objectMapper.writeValueAsString(stockTransfer));

		return "editStockTransfer";
	}

	@PostMapping(path = "/update")
	private String updateStockTransfer(@RequestBody @Validated() StockTransfer stockTransfer) {
		logger.info("updateStockTransfer->fired");
		logger.info("stockTransfer=" + stockTransfer);

		stockTransfer = stockTransferService.update(stockTransfer);

		return "success";
	}

	@PostMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {
		logger.info("delete->fired");

		logger.info("id=" + id);

		stockTransferService.delete(id);

		return "success";
	}

	@GetMapping(path = "/{id}/print")
	private String printStockTransfer(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("printStockTransfer->fired");
		StockTransfer stockTransfer = stockTransferService.findOne(id);
		model.addAttribute("stockTransfer", stockTransfer);

		return "printStockTransfer";
	}
}
