package com.joh.esms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.model.Setting;
import com.joh.esms.service.SettingService;

@Controller()
@RequestMapping(path = "/settings")
public class SettingControler {

	private static final Logger logger = Logger.getLogger(AppController.class);

	@Autowired
	private SettingService settingService;

	@Autowired
	ServletContext context;

	@GetMapping(path = "/add")
	public String settings(Model model) {

		Setting setting = settingService.findSetting();
		if (setting != null) {
			model.addAttribute("setting", setting);
		} else {
			model.addAttribute("setting", new Setting());
		}

		return "settings";
	}

	@PostMapping(path = "/add")
	public String add(@RequestParam MultipartFile file, @Validated() Setting setting, BindingResult result, Model model)
			throws IOException {
		logger.info("add->fired");

		logger.info("setting=" + setting);

		logger.info("error=" + result.getAllErrors());

		if (result.hasErrors()) {
			model.addAttribute("setting", setting);

			return "settings";
		}

		if (file.getSize() > 0) {
			String uploadPath = context.getRealPath("") + File.separator + "/resources/img/logo.png";

			try (OutputStream os = new FileOutputStream(uploadPath)) {
				os.write(file.getBytes());
			}
		}

		settingService.save(setting);

		return "redirect:/settings/add";
	}

}
