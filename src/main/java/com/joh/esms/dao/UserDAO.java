package com.joh.esms.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.joh.esms.model.User;

public interface UserDAO extends CrudRepository<User, Integer> {

	User findByUserName(String userName);

	@Modifying()
	@Query("UPDATE User u set u.password =:password where u.userName =:userName")
	@Transactional
	void updatePassword(@Param("userName") String userName, @Param("password") String password);

}
