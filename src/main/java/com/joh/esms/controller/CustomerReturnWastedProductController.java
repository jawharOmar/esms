package com.joh.esms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.joh.esms.domain.model.JsonResponse;
import com.joh.esms.domain.model.WastedProductsD;
import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerReturnWastedProduct;
import com.joh.esms.model.Product;
import com.joh.esms.model.Stock;
import com.joh.esms.service.CustomerReturnWastedProductService;
import com.joh.esms.service.CustomerService;
import com.joh.esms.service.ProductService;
import com.joh.esms.service.StockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller()
@RequestMapping(path = "/customerReturnWastedProducts")
public class CustomerReturnWastedProductController {

	private static final Logger logger = Logger.getLogger(CustomerReturnWastedProductController.class);

	@Autowired
	private CustomerReturnWastedProductService wastedProductService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private StockService stockService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	public String getAllCustomerWastedProduct(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllCustomerWastedProduct->fired");
		logger.info("from=" + from);
		logger.info("to=" + to);

		List<CustomerReturnWastedProduct> wastedProducts = wastedProductService.findAllByReturnDateBetween(from, to);
		logger.info("wastedProducts=" + wastedProducts);

		model.addAttribute("wastedProducts", wastedProducts);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "customerReturnWastedProduct";
	}

	@GetMapping("/wastedProduct")
	public String getAllWastedProduct(Model model) {
		logger.info("getAllWastedProduct->fired");

		List<WastedProductsD> wastedProductsDS = wastedProductService.findAllWastedProduct();
		logger.info("wastedProducts=" + wastedProductsDS);

		model.addAttribute("wastedProducts", wastedProductsDS);

		return "showWastedProduct";
	}

	@GetMapping(path = "/add")
	private String getAddingCustomerWastedProductProduct(Model model) throws JsonProcessingException {
		logger.info("getAddingCustomerWastedProductProduct->fired");

		Iterable<Customer> customers = customerService.findAll();

		Iterable<Stock> stocks = stockService.findAll();

		logger.info("customers=" + customers);

		Iterable<Product> products = productService.findAll();

		logger.info("products=" + products);

		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));
		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));
		model.addAttribute("jsonWastedProduct", objectMapper.writeValueAsString(new CustomerReturnWastedProduct()));

		return "addCustomerReturnWastedProduct";
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addCustomerReturnWastedProduct(@Valid CustomerReturnWastedProduct wastedProduct,
			BindingResult result, Locale locale) throws IOException {
		logger.info("addCustomerReturnWastedProduct->fired");

		logger.info("wastedProduct=" + wastedProduct);
		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new CusDataIntegrityViolationException("You are entered bad input please fill the data correctly");
		}

		wastedProduct = wastedProductService.save(wastedProduct);
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + wastedProduct.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/edit/{id}")
	private String getEditingCustomerWasted(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("getEditingCustomerWasted->fired");
		logger.info("customerReturnWastedProductID=" + id);

		CustomerReturnWastedProduct wastedProduct = wastedProductService.findOne(id);

		wastedProduct.getCustomer().setCustomerPayments(new ArrayList<>());

		logger.info("wastedProduct=" + wastedProduct);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		Iterable<Customer> customers = customerService.findAll();

		Iterable<Stock> stocks = stockService.findAll();

		logger.info("customers=" + customers);

		Iterable<Product> products = productService.findAll();

		logger.info("products=" + products);

		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));
		model.addAttribute("jsonProducts", objectMapper.writeValueAsString(products));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));
		model.addAttribute("jsonWastedProduct", objectMapper.writeValueAsString(wastedProduct));

		return "editCustomerReturnWastedProduct";
	}

	@PostMapping(path = "/update")
	@ResponseBody
	private JsonResponse update(@Valid CustomerReturnWastedProduct wastedProduct, BindingResult result, Locale locale) {
		logger.info("update->fired");

		logger.info("wastedProduct=" + wastedProduct);
		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new CusDataIntegrityViolationException("You are entered bad input please fill the data correctly");
		}

		wastedProduct = wastedProductService.update(wastedProduct);
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + wastedProduct.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/{id}/print")
	private String printCustomerReturnWastedProduct(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("printCustomerReturnWastedProduct->fired");
		CustomerReturnWastedProduct wastedProduct = wastedProductService.findOne(id);
		model.addAttribute("wastedProduct", wastedProduct);
		return "printCustomerWastedProduct";
	}

	@PostMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {
		logger.info("delete->fired");
		logger.info("customerReturnWastedProductID=" + id);
		wastedProductService.delete(id);
		return "success";
	}

}
