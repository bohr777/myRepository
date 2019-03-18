package com.oracle.boot.orderMeal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.dataObject.Seller;

@Repository("sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, Integer> {

	Seller findBySusernameAndSpwd(String susername,String spwd);
}
