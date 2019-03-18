package com.oracle.boot.orderMeal.service;

import java.util.List;


import com.oracle.boot.orderMeal.dataObject.Orders;

import cn.itcast.common.page.Pagination;

public interface OrdersService {

	List<Orders> findAll();
	
	void save(Orders order);
	
	void delete(Orders order);
	
	void update(Orders order);
	
	Orders findById(Integer oid);
	
	Pagination findAllOrders(Orders order,Integer pageNo);
}
