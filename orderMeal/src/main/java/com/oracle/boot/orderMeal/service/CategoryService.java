package com.oracle.boot.orderMeal.service;

import java.util.List;

import com.oracle.boot.orderMeal.dataObject.Category;


public interface CategoryService {

	List<Category> findAll();
	
	void save(Category category);
	
	void delete(Category category);
	
	void update(Category category);
	
	Category findById(Integer categoryid);
	
	Category findByCtype(Integer ctype);
}
