package com.joh.esms.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.joh.esms.model.Vendor;
import com.joh.esms.model.VendorPayment;
import com.joh.esms.service.OrderProductStepUpService;
import com.joh.esms.service.VendorPaymentService;
import com.joh.esms.service.VendorService;

@Controller()
@RequestMapping(path = "/vendorPayments")
public class VendorPaymentController {

	private static final Logger logger = Logger.getLogger(VendorPaymentController.class);

	@Autowired
	private VendorService vendorService;

	@Autowired
	private VendorPaymentService vendorPaymentService;

	@Autowired
	private OrderProductStepUpService orderProductStepUpService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	private String getCustomerPaymentsByDate(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerPaymentsByDate->fired");

		Iterable<VendorPayment> vendorPayments = vendorPaymentService.findAllByTimeBetween(from, to);

		logger.info("vendorPayments=" + vendorPayments);

		model.addAttribute("vendorPayments", vendorPayments);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "vendorPayments";
	}

	@GetMapping(path = "/vendor/{id}")
	public String getVendorPayments(@PathVariable int id, Model model) {
		logger.info("getVendorPayments->fired");
		logger.info("id=" + id);

		List<VendorPayment> vendorPayments = vendorPaymentService.findAllByVendorId(id);

		Vendor vendor = vendorService.findOne(id);

		model.addAttribute("vendor", vendor);

		model.addAttribute("vendorPayments", vendorPayments);

		return "vendorVendorPayments";
	}

	@GetMapping(path = "/add")
	private String getAddingVendorPayment(Model model) throws JsonProcessingException {
		logger.info("getAddingVendorPayment->fired");

		VendorPayment vendorPayment = new VendorPayment();

		Iterable<Vendor> vendors = vendorService.findAll();

		logger.info("vendors=" + vendors);

		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("jsonVendors", objectMapper.writeValueAsString(vendors));

		model.addAttribute("jsonVendorPayment", objectMapper.writeValueAsString(vendorPayment));

		return "addVendorPayment";
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addVendorPayment(@RequestBody @Validated() VendorPayment vendorPayment, Locale locale) {
		logger.info("addVendorPayment->fired");

		logger.info("vendorPayment=" + vendorPayment);

		vendorPayment = vendorPaymentService.save(vendorPayment);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + vendorPayment.getId());

		return jsonResponse;
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteVendorPayment(@PathVariable int id) {
		logger.info("deleteVendorPayment->fired");
		vendorPaymentService.delete(id);
		return "success";
	}

	@GetMapping(path = "/{id}/print")
	public String vendorPaymentPrint(@PathVariable int id, Model model) {
		logger.info("vendorPaymentPrint->fired");
		logger.info("id=" + id);

		VendorPayment vendorPayment = vendorPaymentService.findOne(id);
		logger.info("vendorPayment=" + vendorPayment);

		Double totalLoan = vendorService.totalVendorLoan(vendorPayment.getVendor().getId());
		logger.info("totalLoan=" + totalLoan);

		model.addAttribute("totalLoan", totalLoan);

		model.addAttribute("vendorPayment", vendorPayment);

		return "vendorPaymentPrint";
	}

}
