package com.oracle.boot.orderMeal.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dataObject.Orders;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.dataObject.User;
import com.oracle.boot.orderMeal.service.OrdersService;
import com.oracle.boot.orderMeal.service.UserService;

@Controller
public class ListenerController {
	
	@Autowired
	private OrdersService orderService;
	@Autowired
	private UserService userService;
	@JmsListener(destination = "mytest.queue")  
    public ModelAndView receiveQueue(Integer oid) {

		Orders order=orderService.findById(oid); 
		Integer uid = order.getUid();
		//用户属性
		User user = userService.findById(uid);
		String odetail = order.getProductsdetail();
		String[] products = odetail.split(",");
		//商品列表
		ArrayList<Product> plist = new ArrayList<>();
		for (String product : products) {
			Product pro = new Product();
			String[] values = product.split("-");
			for(int i=0;i<3;i++){
				int j=i%3;
				switch(j){
					case 0:
						pro.setPname(products[i]);
						break;
					case 1:
						pro.setPsize(products[i]);
						break;
					case 2:
						pro.setPcount(Integer.parseInt(products[i]));
						break;
					
				}
				
			}
			plist.add(pro);
		}
		ModelAndView mav = new ModelAndView("buyer/acceptorder","user",user);
		mav.addObject("plist",plist);
		return mav;   
    }  
}
