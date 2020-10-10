package com.joh.esms.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joh.esms.domain.model.CustomerInvoiceD;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerOrder;
import com.joh.esms.model.CustomerPayment;
import com.joh.esms.model.PriceCategory;
import com.joh.esms.service.CustomerOrderService;
import com.joh.esms.service.CustomerPaymentService;
import com.joh.esms.service.CustomerService;
import com.joh.esms.service.PriceCategoryService;

@Controller()
@RequestMapping(path = "/customers")
public class CustomerController {

	private static final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private CustomerPaymentService customerPaymentService;

	@Autowired
	private PriceCategoryService priceCategoryService;

	@GetMapping()
	public String getAllCustomers(Model model) {
		logger.info("getAllCustomers->fired");

		Iterable<Customer> customers = customerService.findAll();

		customers.forEach(e -> {
			e.setCustomerPayments(null);
		});
		model.addAttribute("customers", customers);

		return "adminCustomers";

	}

	@GetMapping(path = "/add")
	private String getAddingCustomer(Model model) {
		logger.info("getAddingCustomer->fired");

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		logger.info("priceCategories=" + priceCategories);

		model.addAttribute("customer", new Customer());

		model.addAttribute("priceCategories", priceCategories);
		return "customer/addCustomer";
	}

	@PostMapping(path = "/add")
	private String addCustomer(@RequestBody @Valid Customer customer, BindingResult result, Model model) {

		logger.info("addCustomer->fired");
		logger.info("customer=" + customer);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer/addCustomer";
		} else {

			if (customer.getPriceCategory().getId() == 0)
				customer.setPriceCategory(null);

			customerService.save(customer);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteVendor(@PathVariable int id) {
		logger.info("deleteVendor->fired");
		customerService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingCustomer(@PathVariable int id, Model model) {
		logger.info("editingCustomer->fired");
		logger.info("id=" + id);
		Customer customer = customerService.findOne(id);
		logger.info("customer=" + customer);

		List<PriceCategory> priceCategories = priceCategoryService.findAll();
		logger.info("priceCategories=" + priceCategories);

		model.addAttribute("priceCategories", priceCategories);

		model.addAttribute("customer", customer);

		return "customer/editCustomer";
	}

	@PostMapping(path = "/update")
	private String updateCustomer(@RequestBody Customer customer, BindingResult result) {
		logger.info("updateCustomer->fired");

		logger.info("customer=" + customer);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			return "customer/editCustomer";
		} else {

			if (customer.getPriceCategory().getId() == 0)
				customer.setPriceCategory(null);
			customerService.update(customer);
			return "success";
		}
	}

	@GetMapping(path = "/{id}/invoice")
	private String invoice(@PathVariable int id, Model model) {
		logger.info("invoice->fired");
		logger.info("id=" + id);
		Customer customer = customerService.findOne(id);

		logger.info("customer=" + customer);

		List<CustomerOrder> customerOrders = customerOrderService.findAllByCustomerId(id);
		List<CustomerPayment> customerPayments = customerPaymentService.findAllByCustomerId(id);
		double totalLoan = customerService.getCustomerTotalLoan(id);

		model.addAttribute("customer", customer);
		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("customerPayments", customerPayments);
		model.addAttribute("totalLoan", totalLoan);

		return "customerInvoice";
	}

	@GetMapping(path = "/{id}/invoiceDetail")
	private String invoiceDetail(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @PathVariable int id,
			Model model) {
		logger.info("invoiceDetail->fired");
		logger.info("id=" + id);

		Customer customer = customerService.findOne(id);

		List<CustomerInvoiceD> customerInvoiceDs = customerService.findAllCustomerInvoice(id, from, to);

		model.addAttribute("customerInvoiceDs", customerInvoiceDs);
		model.addAttribute("customer", customer);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "customerInvoiceDetail";
	}

	@GetMapping(path = "/{id}/printInvoice")
	private String printInvoice(@PathVariable int id, Model model) {
		logger.info("invoice->fired");
		logger.info("id=" + id);
		Customer customer = customerService.findOne(id);

		logger.info("customer=" + customer);

		List<CustomerOrder> customerOrders = customerOrderService.findAllByCustomerId(id);
		List<CustomerPayment> customerPayments = customerPaymentService.findAllByCustomerId(id);
		double totalLoan = customerService.getCustomerTotalLoan(id);

		model.addAttribute("customer", customer);
		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("customerPayments", customerPayments);
		model.addAttribute("totalLoan", totalLoan);

		return "customerPrintInvoice";
	}

	@PostMapping(value = "/searchCustomer", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchForCustomer(@RequestParam("keyword") String keyword) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(customerService.findCustomersByNameOrPhone(keyword));
	}

	@PostMapping(value = "/searchCustomerId", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String searchForCustomer(@RequestParam("id") Integer id) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = customerService.findOne(id);
		customer.setPassword(null);
		return objectMapper.writeValueAsString(customer);
	}

}
