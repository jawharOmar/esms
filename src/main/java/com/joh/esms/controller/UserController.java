package com.joh.esms.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.model.Role;
import com.joh.esms.model.User;
import com.joh.esms.service.RoleService;
import com.joh.esms.service.UserService;

@Controller()
@RequestMapping(path = "/users")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(path = "/userManager")
	public String getUserManager(Model model, Locale locale) throws JsonProcessingException {
		logger.info("getUserManager->fired");
		Iterable<User> users = userService.findAll();

		Iterable<Role> roles = roleService.findAll();

		ObjectMapper objectMapper = new ObjectMapper();

		roles.forEach(e -> {
			e.setName(messageSource.getMessage(e.getName(), null, locale));
		});

		users.forEach(e -> e.setPassword(null));// not let password go to ui
		model.addAttribute("jsonUsers", objectMapper.writeValueAsString(users));
		model.addAttribute("jsonRoles", objectMapper.writeValueAsString(roles));

		return "userManager";
	}

	@PostMapping(path = "/add")
	private String addUser(@RequestBody @Valid User user, BindingResult result, Model model) {

		logger.info("addUser->fired");
		logger.info("user=" + user);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new RuntimeException("data is not right");
		}
		userService.save(user);

		return "success";

	}

	@PostMapping(path = "/update")
	private String updateUser(@RequestBody @Valid User user, BindingResult result, Model model) {
		logger.info("updateUser->fired");

		logger.info("updateUser->fired");
		logger.info("user=" + user);

		logger.info("error=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new RuntimeException("data is not right");
		}
		userService.update(user);

		return "success";

	}

}
