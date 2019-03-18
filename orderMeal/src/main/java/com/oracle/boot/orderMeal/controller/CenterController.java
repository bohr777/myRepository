package com.oracle.boot.orderMeal.controller;

import java.util.List;

import javax.persistence.Index;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dao.SellerRepository;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.dataObject.Repertory;
import com.oracle.boot.orderMeal.dataObject.Seller;
import com.oracle.boot.orderMeal.service.CategoryService;
import com.oracle.boot.orderMeal.service.ProductService;
import com.oracle.boot.orderMeal.service.RepertoryService;
import com.oracle.boot.orderMeal.service.SellerService;

@Controller
public class CenterController {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RepertoryService repertoryService;

	//去登录页
		@RequestMapping("toLogin")
		public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response) {
			//查询所有商家
			List<Seller> slist = sellerService.findAll();
			//查看cookie中是否有值
			Cookie[] c = request.getCookies();
			//定义两个全局变量（可将局部中的值带到全局使用）
			String susername = null;
			String spwd = null;
			//如果cookie中有值
			if(c!=null){
				//遍历cookie（键、值：账号名、密码）
				for (Cookie cookie : c) {
					//遍历商家集合（商家表中有账号名称和密码）
					for(Seller seller:slist){
						//如果cookie中的键（账号名）  与  商家的账号名一致
						if(cookie.getName().equals(seller.getSusername())){
							//将cookie的键（账号名）赋给 全局变量的姓名、密码空串
							susername = cookie.getName();
							spwd = cookie.getValue();
						}
					}
				}
			}
			//执行上述代码后跳转到登录页面，把从cookie中取出的键（账号名）  赋予的字符串   传入前台   输入账号名的input标签的  value中
			ModelAndView mav = new ModelAndView("seller/login","susername",susername);
			mav.addObject("spwd", spwd);
			return mav;
		}
		
		//点击登录按钮
		@RequestMapping("login")
		public String login(String susername, String spwd,String remember, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
			//判断是否点击“请记住我按钮”。空为没点击
			if(remember==null){
				//根据用户名和密码查询商家
				Seller seller = sellerService.findBySusernameAndSpwd(susername, spwd);
				//商家对象有用户名和密码
				if (seller != null) {
					//将用户名和密码存入session
					session.setAttribute("seller", seller);
					//跳至登录页面
					return "redirect:index";
				} else {//商家对象没有用户名和密码，跳到错误页
					return "seller/error";
				}
			}else{//如果点击了“请记住我按钮”，创建cookie
				Cookie cookie = new Cookie(susername, spwd);
				//设置cookie的生命周期
				cookie.setMaxAge(60*60*24*365);
				//将账号、密码存入cookie中
				response.addCookie(cookie);
				
				Seller seller = sellerService.findBySusernameAndSpwd(susername, spwd);
				if (seller != null) {
					session.setAttribute("seller", seller);
					return "redirect:index";
				} else {
					return "seller/error";
				}
				
			}

		}

	@RequestMapping("toToLogin")
	public String toToLogin() {
		return "seller/tologin";
	}

	@RequestMapping("index")
	public String index(HttpSession session) {
		if (session.getAttribute("seller") == null) {
			return "seller/tologin";
		} else {
			return "seller/index";
		}
	}

	

	@RequestMapping("todemo")
	public ModelAndView todemo(Integer sid){
		System.out.println(sid);
		ModelAndView mav=new ModelAndView("buyer/demo","fclist",categoryService.findAll());
	    mav.addObject("plist",productService.findAll());
	    
	    List<Product> c1Product = productService.findByCtype(10001);
	    for (Product product1 : c1Product) {
	    	product1.setCname(categoryService.findByCtype(product1.getCtype()).getCname());
		}
	    mav.addObject("c1Product", c1Product);
	    
	    List<Product> c2Product = productService.findByCtype(10002);
	    for (Product product2 : c2Product) {
	    	product2.setCname(categoryService.findByCtype(product2.getCtype()).getCname());
		}
	    mav.addObject("c2Product", c2Product);
	    
	    List<Product> c3Product = productService.findByCtype(10003);
	    for (Product product3 : c3Product) {
	    	product3.setCname(categoryService.findByCtype(product3.getCtype()).getCname());
		}
	    mav.addObject("c3Product", c3Product);
	    
	    List<Product> c4Product = productService.findByCtype(10004);
	    for (Product product4 : c4Product) {
	    	product4.setCname(categoryService.findByCtype(product4.getCtype()).getCname());
		}
	    mav.addObject("c4Product", c4Product);
	    
	    List<Product> c5Product = productService.findByCtype(10005);
	    for (Product product5 : c5Product) {
	    	product5.setCname(categoryService.findByCtype(product5.getCtype()).getCname());
		}
	    mav.addObject("c5Product", c5Product);
	    
	    List<Product> c6Product = productService.findByCtype(10006);
	    for (Product product6 : c6Product) {
	    	product6.setCname(categoryService.findByCtype(product6.getCtype()).getCname());
		}
	    mav.addObject("c6Product", c6Product);
	    
	    List<Repertory> r1List =  repertoryService.findByPid(1);
	    mav.addObject("r1List", r1List);
	    
	    List<Seller> slist = sellerService.findAll();
	    mav.addObject("sList", slist);
	    
	    mav.addObject("sid", sid);
	    return mav;
		}

	@RequestMapping("toorder")
	public String toorder() {
		return "buyer/order";
	}
	
	
	
	@RequestMapping("/changePrice")
	@ResponseBody
	public String changePrice(Integer pid,String size) {
		return String.valueOf(repertoryService.findByPidAndSize(pid, size).getRprice());
	}
	
	@RequestMapping("/findPidByProductName")
	@ResponseBody
	public String findPidByProductName(String name) {
		Product product = productService.findByPname(name);
		Integer pid = product.getPid();
		return String.valueOf(pid);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
