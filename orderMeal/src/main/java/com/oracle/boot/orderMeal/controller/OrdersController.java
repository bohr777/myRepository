package com.oracle.boot.orderMeal.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dataObject.Category;
import com.oracle.boot.orderMeal.dataObject.Orders;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.service.OrdersService;
import com.oracle.boot.orderMeal.service.UserService;

import cn.itcast.common.page.Pagination;

@Controller
public class OrdersController {
//	List<E>
	@Autowired
	private OrdersService orderService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("orderList")
	public ModelAndView orderList(Orders orders,Integer pageNo){
		if (orders.getSize() == null) {
			orders.setSize(8);
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		Pagination page = orderService.findAllOrders(orders, pageNo);
		List olist = page.getList();
		
		for (Object orders2 : olist) {
			Orders orders1 = (Orders)orders2;
			String uname = userService.findById(orders1.getUid()).getUname();
			orders1.setUname(uname);
		}
		ModelAndView mav = new ModelAndView("seller/orders","page",page);
		return mav;
	}
	

}
