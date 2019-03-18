package com.oracle.boot.orderMeal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Seller;
import com.oracle.boot.orderMeal.dataObject.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUnameAndUpwd(String uname,String upwd);
}
