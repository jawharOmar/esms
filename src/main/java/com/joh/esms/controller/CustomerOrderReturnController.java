package com.joh.esms.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.joh.esms.domain.model.ProductD;
import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerOrderReturn;
import com.joh.esms.model.Stock;
import com.joh.esms.service.CustomerOrderReturnService;
import com.joh.esms.service.CustomerService;
import com.joh.esms.service.ProductService;
import com.joh.esms.service.StockService;
import com.joh.esms.validator.OrderProductStepUpValidator;

@Controller()
@RequestMapping(path = "/customerOrderReturns")
public class CustomerOrderReturnController {

	private static final Logger logger = Logger.getLogger(CustomerOrderReturnController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderReturnService customerOrderReturnService;

	@Autowired
	private ProductService productService;
	

	@Autowired
	private StockService stockService;

	@GetMapping()
	public String getAllCustomerOrderReturn(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllCustomerOrderReturn->fired");
		logger.info("from=" + from);
		logger.info("to=" + to);

		List<CustomerOrderReturn> customerOrderReturns = customerOrderReturnService.findAllByTimeBetween(from, to);
		logger.info("customerOrderReturns=" + customerOrderReturns);

		model.addAttribute("customerOrderReturns", customerOrderReturns);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "customerOrderReturns";
	}

	@GetMapping(path = "/detail")
	public String getAllCustomerOrderReturnDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllCustomerOrderReturnDetail->fired");
		logger.info("from=" + from);
		logger.info("to=" + to);

		List<CustomerOrderReturn> customerOrderReturns = customerOrderReturnService.findAllByTimeBetween(from, to);
		logger.info("customerOrderReturns=" + customerOrderReturns);

		model.addAttribute("customerOrderReturns", customerOrderReturns);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "customerOrderReturnsDetail";
	}

	@GetMapping(path = "/add")
	private String getAddingCustomerOrderReturn(Model model) throws JsonProcessingException {
		logger.info("getAddingCustomerOrderReturn->fired");

		ObjectMapper objectMapper = new ObjectMapper();
		
		Iterable<Stock> stocks = stockService.findAll();

		Iterable<Customer> customers = customerService.findAll();
		logger.info("customers=" + customers);

		Iterable<ProductD> productDs = productService.findStock();
		logger.info("productDs=" + productDs);

		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));
		model.addAttribute("jsonCustomerOrderReturn", objectMapper.writeValueAsString(new CustomerOrderReturn()));
		model.addAttribute("jsonProductDs", objectMapper.writeValueAsString(productDs));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));

		return "addCustomerOrderReturn";
	}

	@PostMapping(path = "/add")
	private String addCustomerOrderReturn(@RequestBody @Validated() CustomerOrderReturn customerOrderReturn,
			BindingResult result, Model model) throws JsonProcessingException {
		logger.info("addCustomerOrderReturn->fired");
		logger.info("customerOrderReturn=" + customerOrderReturn);
		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new CusDataIntegrityViolationException("You are entered bad input please fill the data correctly");
		}
		customerOrderReturnService.save(customerOrderReturn);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String getEdittingCustomerOrderReturn(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("getEdittingCustomerOrderReturn->fired");
		logger.info("orderProductId=" + id);

		CustomerOrderReturn customerOrderReturn = customerOrderReturnService.findOne(id);

		logger.info("customerOrderReturn=" + customerOrderReturn);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		Iterable<Customer> customers = customerService.findAll();
		Iterable<Stock> stocks = stockService.findAll();

		logger.info("customers=" + customers);

		Iterable<ProductD> productDs = productService.findStock();
		logger.info("productDs=" + productDs);

		model.addAttribute("jsonCustomers", objectMapper.writeValueAsString(customers));
		model.addAttribute("jsonCustomerOrderReturn", objectMapper.writeValueAsString(customerOrderReturn));
		model.addAttribute("jsonProductDs", objectMapper.writeValueAsString(productDs));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));

		return "editCustomerOrderReturn";
	}

	@PostMapping(path = "/update")
	private String updateCustomerOrderReturn(
			@RequestBody @Validated(OrderProductStepUpValidator.Edit.class) CustomerOrderReturn customerOrderReturn,
			BindingResult result, Model model) throws JsonProcessingException {
		logger.info("updateCustomerOrderReturn->fired");
		logger.info("customerOrderReturn=" + customerOrderReturn);

		CustomerOrderReturn customerOrderReturn1 = customerOrderReturnService.update(customerOrderReturn);
		model.addAttribute("url","customerOrderReturns/edit/"+customerOrderReturn1.getId());
		return "successURL";
	}

	@GetMapping(path = "/{id}/print")
	private String printCustomerOrderReturn(@PathVariable int id, Model model) {
		logger.info("printCustomerOrderReturn->fired");
		CustomerOrderReturn customerOrderReturn = customerOrderReturnService.findOne(id);

		logger.info("customerOrderReturn=" + customerOrderReturn);

		model.addAttribute("customerOrderReturn", customerOrderReturn);
		model.addAttribute("totalLoan",
				customerService.getCustomerTotalLoan(customerOrderReturn.getCustomer().getId()));

		return "printCustomerOrderReturn";
	}

	@PostMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {
		logger.info("delete->fired");
		logger.info("orderProductStepUpId=" + id);
		customerOrderReturnService.delete(id);
		return "success";
	}
	

}
