package com.oracle.boot.orderMeal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Discount;

@Repository("discountRepository")
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

	List<Discount> findBySid(Integer sid);
}
