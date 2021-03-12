package com.joh.esms.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joh.esms.model.Account;
import com.joh.esms.model.AccountTransaction;
import com.joh.esms.service.AccountService;
import com.joh.esms.service.AccountTransactionService;

@Controller()
@RequestMapping(path = "/accounts")
public class AccountController {

	private static final Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTransactionService accountTransactionService;

	@GetMapping(path = "/main")
	private String getAccount(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAccount->fired");

		Account account = accountService.findOne(1);

		List<AccountTransaction> accountTransactions;

		Double oldBalance = null;

		if (from != null && to != null) {

			accountTransactions = accountTransactionService.findAllByTimeBetweenAndAccountId(from, to, account.getId());

			double sum = accountTransactions.stream().mapToDouble(e -> e.getAmount()).sum();

			oldBalance = account.getBalance() - sum;

			model.addAttribute("from", from);
			model.addAttribute("to", to);

		} else {
			accountTransactions = accountTransactionService.findAllByAccountId(account.getId());
		}

		logger.info("account=" + account);
		model.addAttribute("accountTransactions", accountTransactions);
		model.addAttribute("account", account);
		model.addAttribute("oldBalance", oldBalance);

		return "accountMain";
	}

}
