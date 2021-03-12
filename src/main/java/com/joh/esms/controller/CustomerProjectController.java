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

import com.joh.esms.model.Customer;
import com.joh.esms.model.CustomerProject;
import com.joh.esms.service.CustomerProjectService;
import com.joh.esms.service.CustomerService;

@Controller()
@RequestMapping(path = "/customerProjects")
public class CustomerProjectController {

	private static final Logger logger = Logger.getLogger(CustomerProjectController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerProjectService customerProjectService;

	@GetMapping(path = "/customer/{id}")
	public String getCustomerProjects(@PathVariable int id, Model model) {
		logger.info("getCustomerProjects->fired");
		logger.info("id=" + id);

		Customer customer = customerService.findOne(id);

		logger.info("customer=" + customer);
		model.addAttribute("customer", customer);

		return "customerProjects";
	}

	@GetMapping(path = "/add/customer/{id}")
	private String getAddingCustomerProject(@PathVariable int id, Model model) {
		logger.info("getAddingCustomerProject->fired");
		logger.info("customerId=" + id);

		CustomerProject customerProject = new CustomerProject();

		model.addAttribute("customerId", id);
		model.addAttribute("customerProject", customerProject);

		return "customerProject/addCustomerProject";
	}

	@PostMapping(path = "/add/customer/{id}")
	private String addCustomerPorject(@PathVariable int id, @RequestBody @Valid CustomerProject customerProject,
			BindingResult result, Model model) {

		logger.info("addCustomerPorject->fired");
		logger.info("customerId=" + id);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customerId", id);
			model.addAttribute("customerProject", customerProject);
			return "customerProject/addCustomerProject";
		} else {

			customerService.addCustomerPorject(id, customerProject);

			return "success";
		}
	}

	@PostMapping(path = "/delete/{id}")
	private String deleteCustomerProject(@PathVariable int id) {
		logger.info("deleteCustomerProject->fired");
		customerProjectService.delete(id);
		return "success";
	}

	@GetMapping(path = "/edit/{id}")
	private String getEditingCustomerProject(@PathVariable int id, Model model) {
		logger.info("getEditingCustomerProject->fired");
		logger.info("id=" + id);
		CustomerProject customerProject = customerProjectService.findOne(id);
		logger.info("customerProject=" + customerProject);

		model.addAttribute("customerProject", customerProject);

		return "customerProject/editCustomerProject";
	}

	@PostMapping(path = "/update")
	private String updateCustomerProject(@RequestBody CustomerProject customerProject, BindingResult result,
			Model model) {
		logger.info("updateCustomerProject->fired");

		logger.info("customerProject=" + customerProject);

		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			model.addAttribute("customerProject", customerProject);
			return "customerProject/editCustomerPorject";
		} else {

			customerProjectService.update(customerProject);
			return "success";
		}
	}

}
