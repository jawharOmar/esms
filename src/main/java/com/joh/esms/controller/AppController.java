package com.joh.esms.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.AccountDAO;
import com.joh.esms.dao.ExpiredDAO;
import com.joh.esms.dao.MinimumDAO;
import com.joh.esms.dao.ReportDAO;
import com.joh.esms.dao.VendorPaymentDAO;
import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.domain.model.ChangePassword;
import com.joh.esms.domain.model.CustomerOrderD;
import com.joh.esms.domain.model.DashboardBalanceD;
import com.joh.esms.domain.model.DashboardExpiredProductD;
import com.joh.esms.domain.model.MinimumProductStockLevelD;
import com.joh.esms.domain.model.SalesReportD;
import com.joh.esms.exception.InputErrorException;
import com.joh.esms.model.Account;
import com.joh.esms.model.User;
import com.joh.esms.model.VendorPayment;
import com.joh.esms.service.AccountTransactionService;
import com.joh.esms.service.InitializingStockFillingService;
import com.joh.esms.service.UserService;

@Controller
public class AppController {

	private static final Logger logger = Logger.getLogger(AppController.class);

	@Autowired
	private VendorPaymentDAO vendorPaymentDAO;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@Autowired
	private ReportDAO report;

	@Autowired
	private MinimumDAO minimumDAO;

	@Autowired
	private ExpiredDAO expiredDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountDAO accountservice;

	@Autowired
	private Environment environment;

	@Autowired
	private InitializingStockFillingService initializingStockFillingService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String login() throws ParseException {

		logger.info("login->fired");

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(environment.getProperty("application.expirationDate"));

		Date now = new Date();

		if (date.before(now)) {
			return "expired";
		}
		return "login";
	}

	@GetMapping("/")
	public String appRoot() {
		logger.info("appRoot->fired");
		return "redirect:/adminRoot";
	}

	@GetMapping("/doit")
	@ResponseBody
	public String test() {

		Iterable<VendorPayment> findAll = vendorPaymentDAO.findAll();

		for (VendorPayment vendorPayment : findAll) {
			accountTransactionService.makeTransaction(AccountTransactionType.VENDOR_PAYMENT, vendorPayment.getId(),
					vendorPayment.getTotalPayment());
		}

		return "done";

	}

	@GetMapping(path = "/adminRoot")
	private String adminRoot(Model model) throws JsonProcessingException {
		logger.info("adminRoot->fired");
		ObjectMapper mapper = new ObjectMapper();

		model.addAttribute("minProducts", minimumDAO.count());
		model.addAttribute("ExpiredProducts", expiredDAO.count());
		model.addAttribute("cus", report.findAdminNotifications());
		Account account = accountservice.findOne(1);
		model.addAttribute("account", account);
		List<CustomerOrderD> customerOrders = report.customerOrders();
		SalesReportD salesReport = report.salesReport();

		List<String> customerOrderLabel = customerOrders.stream().map(e -> e.getDate()).collect(Collectors.toList());
		List<Double> customerOrderData = customerOrders.stream().map(e -> e.getAmount()).collect(Collectors.toList());

		List<String> salesLabel = Arrays.asList("ORDER", "PAYMENT", "LOAN", "RETURN");
		List<Double> salesData = Arrays.asList(salesReport.getOrder(), salesReport.getPayment(), salesReport.getLoan(),
				salesReport.getOrderReturn());

		model.addAttribute("customerOrderLabel", mapper.writeValueAsString(customerOrderLabel));
		model.addAttribute("customerOrderData", mapper.writeValueAsString(customerOrderData));
		model.addAttribute("salesLabel", salesLabel);
		model.addAttribute("salesData", mapper.writeValueAsString(salesData));

		DashboardBalanceD dashboardBalanceD = report.getBalance();
		model.addAttribute(dashboardBalanceD);

		List<MinimumProductStockLevelD> minimumStockLevelProducts = report.getMinimumStockLevelProducts();
		model.addAttribute("minimumStockLevelProducts", minimumStockLevelProducts);

		List<DashboardExpiredProductD> dashboardExpiredProductDs = report.getExpiredProducts();
		model.addAttribute("dashboardExpiredProductDs", dashboardExpiredProductDs);

		return "adminRoot";
	}

	@ResponseBody
	@GetMapping(path = "/minProducts", produces = "application/json;charset=UTF-8")
	private String getMinProducts() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return "{\"data\":" + mapper.writeValueAsString(minimumDAO.findAll()) + "}";
	}

	@ResponseBody
	@GetMapping(path = "/ExpiredProducts", produces = "application/json;charset=UTF-8")
	private String getExpiredProducts() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return "{\"data\":" + mapper.writeValueAsString(expiredDAO.findAll()) + "}";
	}

	@GetMapping("/jsLang.js")
	public String getJavaScriptLang() {
		logger.info("getJavaScriptLang->fired");
		return "jsLang";
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody @Valid() ChangePassword changePassword, BindingResult result,
			Principal principal, Locale locale) {
		logger.info("changePassword->fired");
		logger.debug("changePassword=" + changePassword);
		logger.debug("principal=" + principal);

		if (result.hasErrors()) {
			throw new InputErrorException();
		}

		if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
			User user = userService.findByUserName(principal.getName());

			if (bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
				String newPassword = bCryptPasswordEncoder.encode(changePassword.getNewPassword());
				userService.updatePassword(user.getUserName(), newPassword);
				return ResponseEntity.ok().build();
			} else {
				logger.info("");
			}
		}
		return ResponseEntity.badRequest().build();

	}

	@GetMapping("/insert")
	@ResponseBody
	public String insert() {
		logger.info("insert->fired");
		 initializingStockFillingService.sendToStock();
		return "success";
	}

}
