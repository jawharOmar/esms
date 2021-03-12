package com.joh.esms.service;

import com.joh.esms.model.User;

public interface UserService {

	User findByUserName(String userName);

	void updatePassword(String userName, String password);

	Iterable<User> findAll();

	User save(User user);

	User update(User user);

	void delete(int id);

	User findOne(int id);

}
