package com.joh.esms.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.SettingDAO;
import com.joh.esms.model.Setting;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingDAO settingDAO;

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
	public Setting save(Setting setting) {
		Setting findTopByOrderById = settingDAO.findTopByOrderById();
		if (findTopByOrderById != null)
			setting.setId(findTopByOrderById.getId());
		return settingDAO.save(setting);
	}

	@Override
	public Setting findSetting() {
		return settingDAO.findTopByOrderById();
	}
}
