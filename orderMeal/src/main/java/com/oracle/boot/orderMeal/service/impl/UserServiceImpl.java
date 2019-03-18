package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.UserRepository;
import com.oracle.boot.orderMeal.dataObject.User;
import com.oracle.boot.orderMeal.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	
	public void save(User user) {
		userRepository.save(user);
	}

	
	public void delete(User user) {
		userRepository.delete(user);
	}

	
	public void update(User user) {
		userRepository.save(user);
	}

	
	public User findById(Integer uid) {
		return userRepository.findById(uid).get();
	}
	
	public User findByUnameAndUpwd(String uname,String upwd){
		return userRepository.findByUnameAndUpwd(uname, upwd);
	}

}
