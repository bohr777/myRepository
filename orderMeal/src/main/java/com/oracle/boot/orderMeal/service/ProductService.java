package com.oracle.boot.orderMeal.service;

import java.util.List;

import com.oracle.boot.orderMeal.dataObject.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {

	List<Product> findAll();
	
	void save(Product product);
	
	void delete(Product product);
	
	void update(Product product);
	
	Product findById(Integer pid);
	
	List<Product> findByCtype(Integer ctype);
	
	public Pagination findAllProduct(Product product,Integer pageNo);
	
	Product findByPname(String name);
}
