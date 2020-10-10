package com.joh.esms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.dao.*;
import com.joh.esms.model.Account;
import com.joh.esms.model.Role;
import com.joh.esms.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joh.esms.domain.model.AccountTransactionType;
import com.joh.esms.domain.model.ChangePassword;
import com.joh.esms.domain.model.NotificationD;
import com.joh.esms.model.User;
import com.joh.esms.model.VendorPayment;

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
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String login() {


		logger.info("login->fired");

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
	private String adminRoot(Model model) {
		logger.info("adminRoot->fired");
		model.addAttribute("minProducts",minimumDAO.count());
        model.addAttribute("ExpiredProducts",expiredDAO.count());
        model.addAttribute("cus",report.findAdminNotifications());
		Account account=	accountservice.findOne(1);
		model.addAttribute("account",account);
		return "adminRoot";
	}

    @ResponseBody
    @GetMapping(path = "/minProducts",produces="application/json;charset=UTF-8")
    private String getMinProducts() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return "{\"data\":"+mapper.writeValueAsString(minimumDAO.findAll())+"}";
    }

    @ResponseBody
    @GetMapping(path = "/ExpiredProducts",produces="application/json;charset=UTF-8")
    private String getExpiredProducts() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return "{\"data\":"+mapper.writeValueAsString(expiredDAO.findAll())+"}";
    }


	@GetMapping("/jsLang.js")
	public String getJavaScriptLang() {
		logger.info("getJavaScriptLang->fired");
		return "jsLang";
	}

	@PostMapping("/changePassword")
	public String changePassword(@Valid() ChangePassword changePassword, BindingResult result, Principal principal) {
		logger.info("changePassword->fired");
		logger.info("changePassword=" + changePassword);
		logger.info("principal=" + principal);

		if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
			User user = userService.findByUserName(principal.getName());

			if (bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
				String newPassword = bCryptPasswordEncoder.encode(changePassword.getNewPassword());
				userService.updatePassword(user.getUserName(), newPassword);
				return "redirect:/login";
			} else {
				logger.info("");
			}
		}

		return "redirect:/adminRoot";

	}

}
