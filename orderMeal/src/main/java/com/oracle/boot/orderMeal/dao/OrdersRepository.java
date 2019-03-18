package com.oracle.boot.orderMeal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.boot.orderMeal.dataObject.Orders;


@Repository("orderRepository")
public interface OrdersRepository extends JpaRepository<Orders, Integer>{

}
