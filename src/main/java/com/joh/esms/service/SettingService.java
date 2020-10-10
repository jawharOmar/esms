package com.joh.esms.service;

import com.joh.esms.model.Setting;
import org.springframework.web.multipart.MultipartFile;

public interface SettingService {

	void delete(int id);

	Setting findOne(int id);

	Setting update(Setting setting, MultipartFile Logo, MultipartFile HeaderImage, MultipartFile FoterImage)
			throws Exception;

	Iterable<Setting> findAll();

}