package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.OrdersRepository;
import com.oracle.boot.orderMeal.dataObject.Orders;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.mapper.OrdersMapper;
import com.oracle.boot.orderMeal.service.OrdersService;

import cn.itcast.common.page.Pagination;

@Service("orderService")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository orderRepository;
	@Autowired
	private OrdersMapper ordersMapper;
	
	
	public List<Orders> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public void save(Orders order) {
		orderRepository.save(order);
	}

	@Override
	public void delete(Orders order) {
		orderRepository.delete(order);
	}

	@Override
	public void update(Orders order) {
		orderRepository.save(order);

	}

	@Override
	public Orders findById(Integer oid) {
		return orderRepository.findById(oid).get();
	}

	@Override
	public Pagination findAllOrders(Orders orders, Integer pageNo) {
		orders.setCurrentPage((pageNo-1)*orders.getSize());
		List<Orders> olist = ordersMapper.findAllOrders(orders);
		Pagination page = new Pagination(pageNo,orders.getSize(),ordersMapper.findOrdersCount(orders));
		StringBuilder params = new StringBuilder();
		params.append("size=" + orders.getSize());
		String url = "orderList";
		page.setList(olist);
		System.out.println("++++++++++++++++++"+olist.get(0).getOid());
		page.pageView(url, params.toString());
		return page;
	}

}
