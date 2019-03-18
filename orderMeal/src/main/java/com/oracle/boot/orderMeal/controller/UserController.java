package com.oracle.boot.orderMeal.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.dataObject.Seller;
import com.oracle.boot.orderMeal.dataObject.User;
import com.oracle.boot.orderMeal.service.CategoryService;
import com.oracle.boot.orderMeal.service.ProductService;
import com.oracle.boot.orderMeal.service.SellerService;
import com.oracle.boot.orderMeal.service.UserService;

@Controller
public class UserController {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private UserService UserService;
	@Autowired
	private CategoryService CategoryService;
	

	@RequestMapping("index1")
	public ModelAndView index1() {
		List<Seller> slist = sellerService.findAll();
		System.out.println("==========]  "+slist.get(0).getSid());
		return new ModelAndView("buyer/index", "slist", slist);
	}

	@RequestMapping("/toUserLogin")
	public String toLogin(){
		return "buyer/login";
	}
	@RequestMapping("/UserLogin")
	public String UserLogin(User user,HttpServletRequest request,HttpServletResponse response){
		String uname = user.getUname();
		String upwd = user.getUpwd();
		User user1 = UserService.findByUnameAndUpwd(uname, upwd);
		String str = "";
		if(user1 != null){
			Cookie cookie = new Cookie("user",uname);
			cookie.setMaxAge(60*60*24*365);
			response.addCookie(cookie);
			
			Cookie cookie1 = new Cookie("user1",user1.getUid().toString());
			cookie1.setMaxAge(60*60*24*365);
			response.addCookie(cookie1);
			return "redirect:index1";
		}else{
			return"buyer/error";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response,HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie c:cookies){
				if(c.getName().equals("user")){
					c.setMaxAge(0);
					response.addCookie(c);
					break;
				}
			}
		}
		return "redirect:toUserLogin";
	}
	
	@RequestMapping("/toZhuCe")
	public String toZhuCe(){
		return "buyer/register";
	}
	@RequestMapping("/ZhuCe")
	public String ZhuCe(User user,HttpServletRequest request){
		UserService.save(user);
		return "buyer/login";
	}

	@RequestMapping("/addCart")
	public String addCart(Integer sid,HttpServletResponse response,HttpServletRequest request,String pname,String psize,Integer buyNum,String price){
		Seller seller  = sellerService.findById(sid);
		String s =null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null ){
			for(Cookie c:cookies){
				if(c.getName().equals(seller.getSname())){
					s=c.getValue();
				}
			}
			if(s==null){
				s = pname+"-"+buyNum+"-"+psize+"-"+price;
			}else{
				s = s+"-"+buyNum;
			}
			Cookie cookie = new Cookie(seller.getSname(),s);
		    response.addCookie(cookie);
		}
		
		return "redirect:";
		
	}
	
}
