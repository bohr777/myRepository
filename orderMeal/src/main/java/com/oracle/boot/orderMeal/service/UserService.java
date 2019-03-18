package com.oracle.boot.orderMeal.service;

import java.util.List;

import com.oracle.boot.orderMeal.dataObject.User;

public interface UserService {
	
	List<User> findAll();
	
	void save(User user);
	
	void delete(User user);
	
	void update(User user);
	
	User findById(Integer uid);

	User findByUnameAndUpwd(String uname,String upwd);

}
