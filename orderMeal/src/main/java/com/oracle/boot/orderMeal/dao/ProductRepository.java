package com.oracle.boot.orderMeal.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByCtype(Integer ctype);
	
	Product findByPname(String name);
}
