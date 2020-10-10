package com.joh.esms.controller;

import java.util.Date;

import javax.validation.Valid;

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

import com.joh.esms.model.Income;
import com.joh.esms.model.IncomeCategory;
import com.joh.esms.service.IncomeCategoryService;
import com.joh.esms.service.IncomeService;

@Controller()
@RequestMapping(path = "/incomes")
public class IncomeController {

	private static final Logger logger = Logger.getLogger(IncomeController.class);

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private IncomeCategoryService incomeCategoryService;

	@GetMapping()
	public String getAllIncome(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllIncome->fired");

		Iterable<Income> incomes = incomeService.findAllByTime(from, to);

		logger.info("incomes=" + incomes);
		model.addAttribute("incomes", incomes);

		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminIncomes";

	}

	@GetMapping(path = "/add")
	private String getAddingIncome(Model model) {
		logger.info("getAddingIncome->fired");

		Iterable<IncomeCategory> incomeCategories = incomeCategoryService.findAll();

		model.addAttribute("incomeCategories", incomeCategories);

		model.addAttribute("income", new Income());

		return "income/addIncome";
	}

	@PostMapping(path = "/add")
	private String addIncome(@RequestBody @Valid Income income, BindingResult result, Model model) {

		logger.info("addIncome->fired");
		logger.info("income=" + income);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {

			Iterable<IncomeCategory> incomeCategories = incomeCategoryService.findAll();

			model.addAttribute("incomeCategories", incomeCategories);

			model.addAttribute("income", income);
			return "income/addIncome";
		} else {
			incomeService.save(income);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteIncome(@PathVariable int id) {
		logger.info("deleteIncome->fired");
		incomeService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingIncome(@PathVariable int id, Model model) {
		logger.info("editingIncome->fired");
		logger.info("id=" + id);
		Income income = incomeService.findOne(id);
		logger.info("income=" + income);

		Iterable<IncomeCategory> incomeCategories = incomeCategoryService.findAll();

		model.addAttribute("incomeCategories", incomeCategories);

		model.addAttribute("income", income);

		return "income/editIncome";
	}

	@PostMapping(path = "/update")
	private String updateIncome(@RequestBody Income income, BindingResult result, Model model) {
		logger.info("updateIncome->fired");

		logger.info("income=" + income);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {

			Iterable<IncomeCategory> incomeCategories = incomeCategoryService.findAll();

			model.addAttribute("incomeCategories", incomeCategories);

			return "income/editIncome";
		} else {

			incomeService.update(income);
			return "success";
		}
	}

}
