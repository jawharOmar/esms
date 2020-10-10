package com.joh.esms.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joh.esms.model.Setting;
import com.joh.esms.service.SettingService;

@Controller()
@RequestMapping(path = "/setting")
public class settingControler {

	@Autowired
	private SettingService settingService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	public String settings(Model model) {

		Setting setting = settingService.findOne(1);
		if (setting != null) {
			model.addAttribute("setting", setting);
		} else {
			model.addAttribute("setting", new Setting());
		}

		return "setting";
	}

	@PostMapping(path = "/add")
	private String addSetting(@RequestParam(value = "files") MultipartFile[] files,
			@ModelAttribute("setting") Setting settings, RedirectAttributes redirectAttributes, Locale locale)
			throws Exception {
		settings.setId(1);
		settingService.update(settings, files[0], files[1], files[2]);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("success", null, locale));

		return "redirect:/setting";

	}

	@PostMapping(path = "/currency")
	private String updateCurrency(HttpServletRequest request, @RequestParam("to") String to,
			@RequestParam("base") String base, @RequestParam("rate") Double rate,
			@RequestParam("reverseRate") Double reverseRate) throws Exception {

		Setting setting;
		if (settingService.findOne(1) != null)
			setting = settingService.findOne(1);
		else {
			setting = new Setting();
			setting.setId(1);
		}
		setting.setBase(base);
		setting.setTo(to);
		setting.setRate(rate);
		setting.setReverseRate(reverseRate);
		settingService.update(setting, null, null, null);
		return "redirect:/";
	}

	@ResponseBody
	@GetMapping(path = "/currency", produces = "application/json;charset=UTF-8")
	public String getProduct() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		if (settingService.findOne(1) != null)
			return mapper.writeValueAsString(settingService.findOne(1));
		else
			return mapper.writeValueAsString(new Setting());
	}

	@RequestMapping(path = "/ajaxtest", method = RequestMethod.GET)
	public @ResponseBody String gg() {
		Setting setting = settingService.findOne(1);
		return setting.getName();
	}

	@RequestMapping(path = "/GetSettingData", method = RequestMethod.GET)
	public @ResponseBody String SettingData() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Setting setting = settingService.findOne(1);
		return mapper.writeValueAsString(setting);
	}

}
