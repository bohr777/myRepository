package com.oracle.boot.orderMeal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.persistence.criteria.Order;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.activeMQ.Producer;
import com.oracle.boot.orderMeal.dataObject.Discount;
import com.oracle.boot.orderMeal.dataObject.Orders;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.dataObject.Seller;
import com.oracle.boot.orderMeal.dataObject.User;
import com.oracle.boot.orderMeal.service.DiscountService;
import com.oracle.boot.orderMeal.service.OrdersService;
import com.oracle.boot.orderMeal.service.SellerService;
import com.oracle.boot.orderMeal.service.UserService;

@Controller
public class UserOrderController {

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private UserService userService;
	@Autowired
	private DiscountService discountService;
	@Autowired
	private Producer producer;

	@RequestMapping("/toOrderDetail")
	public ModelAndView toOrderDetail(String sid, HttpServletRequest request, String str, Model model) {
		Integer psid = Integer.parseInt(sid);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			User user = new User();
			boolean flag = false;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user1")) {
					// 详情页买家信息赋值
					String suid = cookie.getValue();
					Integer uid = Integer.parseInt(suid);
					user = userService.findById(uid);
					flag = true;
				}
			}
			if (!flag) {
				return new ModelAndView("buyer/login", "qwe", "meiuid");
			} else {
				// 详情页店家信息
				Seller seller = sellerService.findById(psid);
				// 详情页买家信息
				// String ssid =sid.toString();
				// 商品列表
				ArrayList<Product> olist = new ArrayList<>();
				// 总价格
				Integer totalPrice = 0;
				// 详情页商品信息
				String pdetail = str.substring(0, str.length()-1);
				String[] products = pdetail.split("-");
				for (int j = 0; j < products.length/4; j++) {
					Product product = new Product();
					for (int i = 0; i < products.length; i++) {
						int k = i % 4;
						switch (k) {
						case 0:
							product.setPname(products[i]);
							break;
						case 1:
							product.setPsize(products[i]);
							break;
						case 2:
							product.setPcount(Integer.parseInt(products[i]));
							break;
						case 3:
							totalPrice += Integer.parseInt(products[i]);
							break;
						}
						
					}
					olist.add(product);
				}
				System.out.println(olist + "----------------");
				// 计算减免
				List<Discount> dlist = discountService.findBySid(psid);
				// 与减免的最小差价
				Integer min = 0;
				for (Discount discount : dlist) {
					if (discount.getFull() < totalPrice) {
						if (min < totalPrice - discount.getFull()) {
							min = totalPrice - discount.getFull();
						}
					}
				}
				// 真正减免的数额
				Integer realDiscount = 0;
				for (Discount discount : dlist) {
					if (discount.getFull() + min == totalPrice) {
						realDiscount = discount.getReduce();
					}
				}
				// 最终的价格
				Integer finalPrice = totalPrice - realDiscount;
				// 跳转到订单详情页面
				ModelAndView mav = new ModelAndView("buyer/order", "seller", seller);
				mav.addObject("user", user);
				mav.addObject("olist", olist);
				mav.addObject("finalPrice", finalPrice);
				return mav;
			}

		} else {
			return new ModelAndView("buyer/login", "qwe", "meicookie");
		}
	}

	// String[] pname,String[] psize,String[] pcount
	@RequestMapping("/realOrder")
	public void realOrder(User user, Seller seller, HttpServletRequest request, Double fprice, String remark,
			String pname) {
		Orders order = new Orders();
		// 是否送达，默认为否
		order.setIssend(0);
		// 订单总金额
		order.setOamount(fprice);
		// 是否删除
		order.setIsdel(1);
		// 创建时间
		order.setOcreatetime(new Date());
		// 订单数量（无用）
		order.setOnum(1);
		// 订单状态（未完成）
		order.setOstatus(0);
		// 支付状态（已支付）
		order.setPaystatus(1);
		// 商品详情
		String pdetail = "1";
		// String[] pname = request.getParameterValues("pname");
		System.out.println("   pname    " + pname);
		// System.out.println(" psize "+psize.length);
		// System.out.println(" pcount "+pcount.length);
		// for (int i=0;i<pname.length;i++) {
		// pdetail+=pname[i]+"-"+psize[i]+"-"+pcount[i]+",";
		// }
		pdetail.substring(0, pdetail.length() - 1);
		order.setProductsdetail(pdetail);
		// 商品描述
		order.setRemark(remark);
		ordersService.save(order);
		int oid = order.getOid();
		// Destination destination = new ActiveMQQueue("mytest.queue");
		// producer.sendMessage(destination, oid);

	}
}
