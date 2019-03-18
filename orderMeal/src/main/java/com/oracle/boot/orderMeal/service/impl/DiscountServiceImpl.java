package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.DiscountRepository;
import com.oracle.boot.orderMeal.dataObject.Discount;
import com.oracle.boot.orderMeal.service.DiscountService;

@Service("discountService")
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountRepository discountRepository;
	
	
	public List<Discount> findAll() {
		return discountRepository.findAll();
	}

	public void save(Discount discount) {
		discountRepository.save(discount);
	}

	public void delete(Discount discount) {
		discountRepository.delete(discount);
	}

	
	public void update(Discount discount) {
		discountRepository.save(discount);
	}

	
	public Discount findById(Integer discountid) {
		return discountRepository.findById(discountid).get();
	}
	
	public List<Discount> findBySid(Integer sid) {
		
		return discountRepository.findBySid(sid);
	}

}
