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

import com.joh.esms.model.Expense;
import com.joh.esms.model.ExpenseCategory;
import com.joh.esms.model.IncomeCategory;
import com.joh.esms.service.ExpenseCategoryService;
import com.joh.esms.service.ExpenseService;

@Controller()
@RequestMapping(path = "/expenses")
public class ExpenseController {

	private static final Logger logger = Logger.getLogger(ExpenseController.class);

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private ExpenseCategoryService expenseCategoryService;

	@GetMapping()
	public String getAllExpenses(@RequestParam(required = false,defaultValue = "-1")  int cat,@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllExpenses->fired");
        Iterable<Expense> expenses;
		if(cat==-1)
		expenses = expenseService.findAll(from, to);
        else
            expenses = expenseService.findbycat(from,to,cat);


        logger.info("expenses=" + expenses);
        model.addAttribute("Category",expenseCategoryService.findAll());
		model.addAttribute("expenses", expenses);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "adminExpenses";

	}

	@GetMapping(path = "/add")
	private String getAddingExpense(Model model) {
		logger.info("getAddingExpense->fired");

		Iterable<ExpenseCategory> expenseCategories = expenseCategoryService.findAll();

		model.addAttribute("expenseCategories", expenseCategories);

		model.addAttribute("expense", new Expense());
		return "expense/addExpense";
	}

	@PostMapping(path = "/add")
	private String addExpense(@RequestBody @Valid Expense expense, BindingResult result, Model model) {

		logger.info("addExpense->fired");
		logger.info("expense=" + expense);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			Iterable<ExpenseCategory> expenseCategories = expenseCategoryService.findAll();

			model.addAttribute("expenseCategories", expenseCategories);

			model.addAttribute("expense", expense);
			return "expense/addExpense";
		} else {

			expenseService.save(expense);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteExpense(@PathVariable int id) {
		logger.info("deleteExpense->fired");
		expenseService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingExpense(@PathVariable int id, Model model) {
		logger.info("editingExpense->fired");
		logger.info("id=" + id);
		Expense expense = expenseService.findOne(id);
		logger.info("expense=" + expense);

		Iterable<ExpenseCategory> expenseCategories = expenseCategoryService.findAll();

		model.addAttribute("expenseCategories", expenseCategories);

		model.addAttribute("expense", expense);

		return "expense/editExpense";
	}

	@PostMapping(path = "/update")
	private String updateExpense(@RequestBody Expense expense, BindingResult result, Model model) {
		logger.info("updateExpense->fired");

		logger.info("expense=" + expense);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			Iterable<ExpenseCategory> expenseCategories = expenseCategoryService.findAll();

			model.addAttribute("expenseCategories", expenseCategories);

			return "expense/editExpense";
		} else {

			expenseService.update(expense);
			return "success";
		}
	}

}