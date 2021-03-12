package com.joh.esms.controller;

import java.util.Date;
import java.util.Locale;

import com.joh.esms.dao.CustomerDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.domain.model.JsonResponse;
import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerPayment;
import com.joh.esms.service.CustomerOrderService;
import com.joh.esms.service.CustomerPaymentService;
import com.joh.esms.service.CustomerService;

@Controller()
@RequestMapping(path = "/customerPayments")
public class CustomerPaymentController {

	private static final Logger logger = Logger.getLogger(CustomerPaymentController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerPaymentService customerPaymentService;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	private String getCustomerPaymentsByDate(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerPaymentsByDate->fired");

		Iterable<CustomerPayment> customerPayments = customerPaymentService.findAllByTimeBetween(from, to);

		logger.info("customerPayments=" + customerPayments);

		model.addAttribute("customerPayments", customerPayments);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "customerPayments";
	}

	@GetMapping(path = "/customer/{id}")
	public String getCustomerPayments(@PathVariable int id, Model model) {
		logger.info("getCustomerPayments->fired");
		logger.info("id=" + id);

		Customer customer = customerService.findOne(id);

		logger.info("customer=" + customer);
		model.addAttribute("customer", customer);

		return "customerCustomerPayments";
	}

	@GetMapping(path = "/add")
	private String getAddingCustomerPayment(Model model) throws JsonProcessingException {
		logger.info("getAddingCustomerPayment->fired");
		CustomerPayment customerPayment = new CustomerPayment();

		ObjectMapper objectMapper = new ObjectMapper();

		model.addAttribute("jsonCustomerPayment", objectMapper.writeValueAsString(customerPayment));

		return "addCustomerPayment";
	}

	@GetMapping(path = "/totalLoan/{id}/{projectId}")
	@ResponseBody
	private Double getAddingCustomerPayment(@PathVariable("id") Integer id,
			@PathVariable("projectId") Integer projectId) throws JsonProcessingException {
		if (id != null && projectId != null) {
			return customerDAO.totalLoanByProject(id, projectId);
		}
		return null;
	}

	@GetMapping(path = "/customerpayment/edit/{id}")
	public String getediting(@PathVariable int id, Model model) {
		logger.info("getCustomerPayments->fired");
		logger.info("id=" + id);

		CustomerPayment customerPayment = customerPaymentService.findOne(id);

		logger.info("customer=" + customerPayment);
		model.addAttribute("customerPayment", customerPayment);

		return "customerPayment/editCustomerPayment";
	}

	@PostMapping(path = "/customerpayment/edit/{id}")
	private String editcustomer(@RequestBody @Validated() CustomerPayment customerPayment, Locale locale) {

		logger.info("editCustomerOrder->fired");

		logger.info("customerPayment=" + customerPayment);
		customerPayment.setCustomer(customerPaymentService.findOne(customerPayment.getId()).getCustomer());

		CustomerPayment customerPaymentold = customerPaymentService.findOne(customerPayment.getId());
		customerPaymentold.setTotalPayment(customerPayment.getTotalPayment());
		customerPaymentold.setCustomerProject(customerPayment.getCustomerProject());
		customerPayment = customerPaymentService.update(customerPaymentold);

		return "success";
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addCustomerPayment(@RequestBody @Validated() CustomerPayment customerPayment, Locale locale) {

		logger.info("addCustomerOrder->fired");

		logger.info("customerPayment=" + customerPayment);

		customerPayment = customerPaymentService.save(customerPayment);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + customerPayment.getId());

		return jsonResponse;
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteCustomerPayment(@PathVariable int id) {
		logger.info("deleteCustomerPayment->fired");
		customerPaymentService.delete(id);
		return "success";
	}

	@GetMapping(path = "/{id}/print")
	public String customerPaymentPrint(@PathVariable int id, Model model) {
		logger.info("customerPaymentPrint->fired");
		logger.info("id=" + id);

		CustomerPayment customerPayment = customerPaymentService.findOne(id);
		logger.info("customerPayment=" + customerPayment);

		double totalLoan = customerService.getCustomerTotalLoan(customerPayment.getCustomer().getId());

		logger.info("totalLoan=" + totalLoan);

		model.addAttribute("totalLoan", totalLoan);

		model.addAttribute("customerPayment", customerPayment);

		return "customerPaymentPrint";
	}

}
