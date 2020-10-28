package com.joh.esms.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.joh.esms.domain.model.JsonResponse;
import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.model.OrderPreProduct;
import com.joh.esms.service.OrderPreProductService;

@Controller()
@RequestMapping(path = "/orderPreProducts")
public class OrderPreProductController {

	private static final Logger logger = Logger.getLogger(OrderPreProductController.class);

	@Autowired
	private OrderPreProductService orderPreProductService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	public String getAllOrderPreProduct(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, Model model) {
		logger.info("getAllOrderPreProduct->fired");
		logger.info("from=" + from);
		logger.info("to=" + to);

		List<OrderPreProduct> orderPreProducts = orderPreProductService.findAllByOrderTimeBetween(from, to);
		logger.info("orderPreProducts=" + orderPreProducts);

		model.addAttribute("orderPreProducts", orderPreProducts);
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		return "orderPreProduct";
	}

	@GetMapping(path = "/add")
	private String getAddingOrderPreProduct(Model model) throws JsonProcessingException {
		logger.info("getAddingOrderPreProduct->fired");

		ObjectMapper objectMapper = new ObjectMapper();

		OrderPreProduct orderPreProduct = new OrderPreProduct();

		model.addAttribute("jsonOrderPreProduct", objectMapper.writeValueAsString(orderPreProduct));

		return "addOrderPreProduct";
	}

	@PostMapping(path = "/add")
	@ResponseBody
	private JsonResponse addOrderPreProduct(@RequestParam(value = "files") MultipartFile[] files,
			@Validated OrderPreProduct orderPreProduct, BindingResult result, Locale locale, Model model)
			throws IOException {
		logger.info("addOrderPreProduct->fired");
		logger.error("files=" + files.length);

		logger.info("orderPreProduct=" + orderPreProduct);
		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new CusDataIntegrityViolationException("You are ented bad input please fill the data correctly");
		}

		orderPreProduct = orderPreProductService.save(orderPreProduct, files);
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + orderPreProduct.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/edit/{id}")
	private String getEdittingOrderPreProduct(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("getEdittingOrderPreProduct->fired");
		logger.info("orderPreProductId=" + id);

		OrderPreProduct orderPreProduct = orderPreProductService.findOne(id);

		logger.info("orderPreProduct=" + orderPreProduct);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		model.addAttribute("jsonOrderPreProduct", objectMapper.writeValueAsString(orderPreProduct));

		return "editOrderPreProduct";
	}

	@PostMapping(path = "/update")
	@ResponseBody
	private JsonResponse update(@RequestParam(value = "files") MultipartFile[] files,
			@Validated OrderPreProduct orderPreProduct, BindingResult result, Model model, Locale locale)
			throws IOException {
		logger.info("update->fired");
		logger.error("files=" + files.length);

		logger.info("orderPreProduct=" + orderPreProduct);
		logger.info("errors=" + result.getAllErrors());
		if (result.hasErrors()) {
			throw new CusDataIntegrityViolationException("You are ented bad input please fill the data correctly");
		}

		orderPreProduct = orderPreProductService.update(orderPreProduct, files);
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setStatus(200);
		jsonResponse.setMessage(messageSource.getMessage("success", null, locale));
		jsonResponse.setEtc("" + orderPreProduct.getId());

		return jsonResponse;
	}

	@GetMapping(path = "/{id}/print")
	private String printOrderProductStepUp(@PathVariable int id, Model model) throws JsonProcessingException {
		logger.info("printOrderProductStepUp->fired");
		OrderPreProduct orderPreProduct = orderPreProductService.findOne(id);
		model.addAttribute("orderPreProduct", orderPreProduct);

		return "printOrderPreProduct";
	}

	@PostMapping(path = "/delete/{id}")
	private String delete(@PathVariable int id) {
		logger.info("delete->fired");
		logger.info("orderPreProductId=" + id);
		orderPreProductService.delete(id);
		return "success";
	}

}
