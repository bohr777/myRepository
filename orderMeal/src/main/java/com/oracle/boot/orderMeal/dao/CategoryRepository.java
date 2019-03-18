package com.oracle.boot.orderMeal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Category;


@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByCtype(Integer ctype);
}
