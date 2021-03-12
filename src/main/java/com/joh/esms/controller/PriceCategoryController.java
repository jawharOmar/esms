package com.joh.esms.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joh.esms.model.PriceCategory;
import com.joh.esms.service.PriceCategoryService;

@Controller
@RequestMapping(path = "/priceCategories")
public class PriceCategoryController {

	private static final Logger logger = Logger.getLogger(CustomerOrderController.class);

	@Autowired
	private PriceCategoryService priceCategoryService;

	@GetMapping()
	public String getAllPriceCategories(Model model) {
		logger.info("getAllPriceCategories->fired");

		Iterable<PriceCategory> priceCategories = priceCategoryService.findAll();

		logger.info("priceCategories=" + priceCategories);
		model.addAttribute("priceCategories", priceCategories);

		return "priceCategories";

	}

	@GetMapping(path = "/add")
	private String getAddingPriceCategory(Model model) {
		logger.info("getAddingPriceCategory->fired");

		model.addAttribute("priceCategory", new PriceCategory());

		return "priceCategory/addPriceCategory";
	}

	@PostMapping(path = "/add")
	private String addPriceCategory(@RequestBody @Valid PriceCategory priceCategory, BindingResult result,
			Model model) {

		logger.info("addPriceCategory->fired");

		logger.info("priceCategory=" + priceCategory);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("priceCategory", priceCategory);
			return "priceCategory/addPriceCategory";
		} else {
			priceCategoryService.save(priceCategory);

			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deletePriceCategory(@PathVariable int id) {
		logger.info("deletePriceCategory->fired");
		priceCategoryService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String editingPriceCategory(@PathVariable int id, Model model) {
		logger.info("editingPriceCategory->fired");
		logger.info("id=" + id);
		PriceCategory priceCategory = priceCategoryService.findOne(id);
		logger.info("priceCategory=" + priceCategory);

		model.addAttribute("priceCategory", priceCategory);

		return "priceCategory/editPriceCategory";
	}

	@PostMapping(path = "/update")
	private String updateDoctor(@RequestBody @Valid PriceCategory priceCategory, BindingResult result) {
		logger.info("updateDoctor->fired");

		logger.info("priceCategory=" + priceCategory);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			return "priceCategory/editPriceCategory";
		} else {
			priceCategoryService.update(priceCategory);
			return "success";
		}
	}

}
