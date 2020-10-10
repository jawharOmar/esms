package com.joh.esms.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joh.esms.dao.SettingDAO;
import com.joh.esms.model.Setting;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingDAO settingDAO;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	HttpServletRequest request;

	@Override
	public Iterable<Setting> findAll() {
		return settingDAO.findAll();
	}

	@Override
	@Transactional
	public void delete(int id) {
		settingDAO.delete(id);
	}

	@Override
	public Setting findOne(int id) {
		return settingDAO.findOne(id);
	}

	@Override
	public Setting update(Setting setting, MultipartFile logo, MultipartFile headerImage, MultipartFile footerImage)
			throws Exception {

		if (logo != null && logo.getSize() != 0) {
			logo.transferTo(resourceLoader.getResource("resources/img/logo.png").getFile());
			setting.setLogo(resourceLoader.getResource("resources/img/logo.png").getFile().getPath());
		}
		if (headerImage != null && headerImage.getSize() != 0) {
			headerImage.transferTo(resourceLoader.getResource("resources/img/HeaderImage.jpg").getFile());
			setting.setHeader_image(resourceLoader.getResource("resources/img/HeaderImage.jpg").getFile().getPath());
		}
		if (footerImage != null && footerImage.getSize() != 0) {
			footerImage.transferTo(resourceLoader.getResource("resources/img/FoterImage.jpg").getFile());
			setting.setFooter_image(resourceLoader.getResource("resources/img/FoterImage.jpg").getFile().getPath());
		}

		return settingDAO.save(setting);
	}
}
