package com.oracle.boot.orderMeal.service;

import java.util.List;

import com.oracle.boot.orderMeal.dataObject.Seller;

public interface SellerService {

	List<Seller> findAll();
	
	void save(Seller seller);
	
	void delete(Seller seller);
	
	void update(Seller seller);
	
	Seller findById(Integer sid);
	
	Seller findBySusernameAndSpwd(String susername,String spwd);
}
