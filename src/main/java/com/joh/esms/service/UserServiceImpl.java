package com.joh.esms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.joh.esms.dao.UserDAO;
import com.joh.esms.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Iterable<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		return userDAO.findByUserName(userName);
	}

	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userDAO.save(user);
	}

	@Override
	public User findOne(int id) {
		return userDAO.findOne(id);
	}

	@Override
	public User update(User user) {
		User savedUser = userDAO.findOne(user.getId());
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			user.setPassword(savedUser.getPassword());
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}

		return userDAO.save(user);
	}

	@Override
	public void updatePassword(String userName, String password) {
		userDAO.updatePassword(userName, password);
	}

	@Override
	public void delete(int id) {
		userDAO.delete(id);
	}

}
