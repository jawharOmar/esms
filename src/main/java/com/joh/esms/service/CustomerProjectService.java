package com.joh.esms.service;

import com.joh.esms.model.CustomerProject;

public interface CustomerProjectService {

	void delete(int id);

	CustomerProject save(CustomerProject customerPoroject);

	CustomerProject update(CustomerProject customerPoroject);

	CustomerProject findOne(int id);

}
