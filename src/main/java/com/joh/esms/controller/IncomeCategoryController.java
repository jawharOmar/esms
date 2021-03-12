package com.joh.esms.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.esms.model.ExpenseCategory;
import com.joh.esms.model.IncomeCategory;
import com.joh.esms.service.IncomeCategoryService;

@Controller()
@RequestMapping(path = "/incomeCategories")
public class IncomeCategoryController {

	private static final Logger logger = Logger.getLogger(IncomeCategoryController.class);

	@Autowired
	private IncomeCategoryService incomeCategoryService;

	@GetMapping()
	public String getAllIncomeCategories(Model model) {
		logger.info("getAllIncomeCategories->fired");

		Iterable<IncomeCategory> incomeCategories = incomeCategoryService.findAll();

		logger.info("incomeCategories=" + incomeCategories);
		model.addAttribute("incomeCategories", incomeCategories);

		return "incomeCategories";

	}

	@GetMapping(path = "/add")
	private String getAddingIncomeCategory(Model model) {
		logger.info("getAddingIncomeCategory->fired");

		model.addAttribute("incomeCategory", new IncomeCategory());
		return "incomeCategory/addIncomeCategory";
	}

	@PostMapping(path = "/add")
	private String addIncomeCategory(@RequestBody @Valid IncomeCategory incomeCategory, BindingResult result,
			Model model) {

		logger.info("addIncomeCategory->fired");
		logger.info("incomeCategory=" + incomeCategory);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("incomeCategory", incomeCategory);
			return "incomeCategory/addIncomeCategory";
		} else {
			incomeCategoryService.save(incomeCategory);
			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteIncomeCategory(@PathVariable int id) {
		logger.info("deleteIncomeCategory->fired");
		incomeCategoryService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingIncomeCategory(@PathVariable int id, Model model) {
		logger.info("editingIncomeCategory->fired");
		logger.info("id=" + id);
		IncomeCategory incomeCategory = incomeCategoryService.findOne(id);
		logger.info("incomeCategory=" + incomeCategory);

		model.addAttribute("incomeCategory", incomeCategory);

		return "incomeCategory/editIncomeCategory";
	}

	@PostMapping(path = "/update")
	private String updateIncomeCategory(@RequestBody IncomeCategory incomeCategory, BindingResult result) {
		logger.info("updateIncomeCategory->fired");

		logger.info("incomeCategory=" + incomeCategory);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			return "incomeCategory/editIncomeCategory";
		} else {

			incomeCategoryService.update(incomeCategory);
			return "success";
		}
	}

}
