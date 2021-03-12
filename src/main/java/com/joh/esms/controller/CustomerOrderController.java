package com.joh.esms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

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
import com.joh.esms.model.CustomerOrder;
import com.joh.esms.model.CustomerOrderDetail;
import com.joh.esms.model.Setting;
import com.joh.esms.model.Stock;
import com.joh.esms.service.CustomerOrderDetailService;
import com.joh.esms.service.CustomerOrderService;
import com.joh.esms.service.CustomerService;
import com.joh.esms.service.SettingService;
import com.joh.esms.service.StockService;

@Controller
@RequestMapping(path = "/customerOrders")
public class CustomerOrderController {

	private static final Logger logger = Logger.getLogger(CustomerOrderController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private CustomerOrderDetailService customerOrderDetailService;

	@Autowired
	private StockService stockService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SettingService settingService;

	@GetMapping(path = "/add")
	private String getAddingCustomerOrder(Model model) throws JsonProcessingException {
		logger.info("getAddingCustomerOrder->fired");

		Iterable<Stock> stocks = stockService.findAll();

		ObjectMapper objectMapper = new ObjectMapper();
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));
		model.addAttribute("jsonCustomerOrder", objectMapper.writeValueAsString(new CustomerOrder()));

		return "adminAddCustomerOrder";
	}

	@PostMapping(path = "/forPrintOnly")
	@ResponseBody
	private JsonResponse addCustomerOrderForPrintOnly(HttpSession httpSession,
			@RequestBody @Validated() CustomerOrder customerOrder, Locale locale) {

		logger.info("addCustomerOrderForPrintOnly->fired");

		logger.info("customerOrder=" + customerOrder);

		httpSession.setAttribute("customerOrder", customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));

		return jsonResponse;
	}

	@GetMapping(path = "/forPrintOnly")
	public String customerOrderForPrintOnly(HttpSession httpSession, Model model) {

		logger.info("customerOrderForPrintOnly->fired");

		if (httpSession.getAttribute("customerOrder") != null) {

			CustomerOrder customerOrder = (CustomerOrder) httpSession.getAttribute("customerOrder");
			logger.info("customerOrder=" + customerOrder);

			model.addAttribute("customerOrder", customerOrder);

			customerOrder.getCustomer()
					.setTotalLoan(customerService.getCustomerTotalLoan(customerOrder.getCustomer().getId()));

			return "getCustomerOrder";
		} else {
			throw new EntityNotFoundException();
		}
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addCustomerOrder(@RequestBody @Validated() CustomerOrder customerOrder, Locale locale) {

		logger.info("addCustomerOrder->fired");

		logger.info("customerOrder=" + customerOrder);

		customerOrder = customerOrderService.save(customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + customerOrder.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/edit/{id}")
	private String getEditingCustomerOrder(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("getEditingCustomerOrder->fired");

		CustomerOrder customerOrder = customerOrderService.findOne(id);

		logger.info("customerOrder=" + customerOrder);

		Iterable<Stock> stocks = stockService.findAll();

		ObjectMapper objectMapper = new ObjectMapper();

		model.addAttribute("jsonCustomerOrder", objectMapper.writeValueAsString(customerOrder));
		model.addAttribute("jsonStocks", objectMapper.writeValueAsString(stocks));

		return "adminEditCustomerOrder";
	}

	@GetMapping(path = "/edit")
	private String getEditingCustomerOrderByInvoiceId(@RequestParam int invoiceId, Model model)
			throws JsonProcessingException {

		logger.info("getEditingCustomerOrderByInvoiceId->fired");

		CustomerOrder customerOrder = customerOrderService.findByInvoiceId(invoiceId);

		return "redirect:/customerOrders/edit/" + customerOrder.getId();
	}

	@PostMapping(path = "/update")
	@ResponseBody
	private JsonResponse udpateCustomerOrder(

			@RequestBody @Validated() CustomerOrder customerOrder) {

		logger.info("udpateCustomerOrder->fired");

		logger.info("customerOrder=" + customerOrder);

		customerOrder = customerOrderService.update(customerOrder);

		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage("success");
		jsonResponse.setEtc("" + customerOrder.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {

		logger.info("udpateCustomerOrder->fired");

		logger.info("id=" + id);

		customerOrderService.delete(id);

		return "success";
	}

	@GetMapping(value = { "/{id}", "/{id}/{showLoan}" })
	public String getCustomerOrder(@PathVariable int id, @PathVariable(required = false) Boolean showLoan,
			Model model) {

		logger.info("getCustomerOrder->fired");

		CustomerOrder customerOrder = customerOrderService.findOne(id);
		logger.info("customerOrder=" + customerOrder);

		model.addAttribute("customerOrder", customerOrder);

		if (showLoan != null && showLoan) {
			model.addAttribute("showLoan", true);
		}

		customerOrder.getCustomer().setTotalLoan(customerService
				.getCustomerTotalLoanByTime(customerOrder.getCustomer().getId(), customerOrder.getOrderTime()));

		Setting setting = settingService.findSetting();
		model.addAttribute("setting", setting);

		if (setting.getPrintCustomerThermal() != null && setting.getPrintCustomerThermal() == 1)

			return "customerOrder/printCustomerOrderThermal";
		else

			return "getCustomerOrder";

	}

	@GetMapping()
	private String getCustomerOrdersByDate(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerOrdersByDate->fired");
		List<CustomerOrder> customerOrders = new ArrayList<>();

		if (from != null && to != null) {
			customerOrders = customerOrderService.findAllByOrderTimeBetween(from, to);
		}

		logger.info("customerOrders=" + customerOrders);

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminCustomerOrders";
	}

	@GetMapping(path = "/sold")
	private String getCustomerOrderProductSold(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getCustomerOrderProductSold->fired");

		List<CustomerOrder> customerOrders = customerOrderService.findAllByOrderTimeBetween(from, to);

		logger.info("customerOrders=" + customerOrders);

		model.addAttribute("customerOrders", customerOrders);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminCustomerOrderProductSold";
	}

	@GetMapping(path = "/productStepUp/{id}")
	private String getAllCustomerOrderByProductStepUp(@PathVariable int id, Model model) {
		logger.info("getAllCustomerOrderByProductStepUp->fired");

		List<CustomerOrder> customerOrders = customerOrderService.findAllCustomerOrderByProductStepUpId(id);

		logger.info("customerOrders=" + customerOrders);

		model.addAttribute("customerOrders", customerOrders);

		return "adminCustomerOrderProductStepUp";
	}

	@GetMapping(path = "/product/{id}")
	private String getAllCustomerOrderByProductId(@PathVariable int id, Model model) {
		logger.info("getAllCustomerOrderByProductId->fired");

		List<CustomerOrderDetail> customerOrderDetails = customerOrderDetailService.findAllByProductId(id);

		logger.info("customerOrderDetails=" + customerOrderDetails);

		model.addAttribute("customerOrderDetails", customerOrderDetails);

		return "adminCustomerOrderProduct";
	}

}
