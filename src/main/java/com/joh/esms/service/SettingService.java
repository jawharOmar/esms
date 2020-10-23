package com.joh.esms.service;

import com.joh.esms.model.Setting;

public interface SettingService {

	void delete(int id);

	Iterable<Setting> findAll();

	Setting save(Setting setting);

	Setting findSetting();

}