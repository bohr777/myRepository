package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.SellerRepository;
import com.oracle.boot.orderMeal.dataObject.Seller;
import com.oracle.boot.orderMeal.service.SellerService;
@Service("sellerService")
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	
	public List<Seller> findAll() {
		return sellerRepository.findAll();
	}

	
	public void save(Seller seller) {
		sellerRepository.save(seller);
	}

	
	public void delete(Seller seller) {
		sellerRepository.delete(seller);
	}

	
	public void update(Seller seller) {
		sellerRepository.save(seller);
	}

	
	public Seller findById(Integer sid) {
		return sellerRepository.findById(sid).get();
	}


	@Override
	public Seller findBySusernameAndSpwd(String susername, String spwd) {
		return sellerRepository.findBySusernameAndSpwd(susername, spwd);
	}

}
