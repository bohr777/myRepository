package com.oracle.boot.orderMeal.service;

import java.util.List;


import com.oracle.boot.orderMeal.dataObject.Discount;

public interface DiscountService {
	
	List<Discount> findAll();
	
	void save(Discount discount);
	
	void delete(Discount discount);
	
	void update(Discount discount);
	
	Discount findById(Integer discountid);
	
	List<Discount> findBySid(Integer sid);
}
