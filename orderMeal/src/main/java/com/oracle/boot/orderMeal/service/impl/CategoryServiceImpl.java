package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.CategoryRepository;
import com.oracle.boot.orderMeal.dataObject.Category;
import com.oracle.boot.orderMeal.service.CategoryService;
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {


	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	
	public void save(Category category) {
		categoryRepository.save(category);
	}


	public void delete(Category category) {
		categoryRepository.delete(category);
	}


	public void update(Category category) {
		categoryRepository.save(category);
	}


	public Category findById(Integer categoryid) {
		return categoryRepository.findById(categoryid).get();
	}


	@Override
	public Category findByCtype(Integer ctype) {
		return categoryRepository.findByCtype(ctype);
	}

}
