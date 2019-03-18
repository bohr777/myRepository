package com.oracle.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.buyer.BuyerService;
import com.oracle.pojo.Buyer;

@Controller
public class LoginController {

	@Autowired
	private BuyerService buyerService;

	// 去登录页面
	@RequestMapping("/toLogin.aspx")
	public String toLogin(String ReturnUrl, Model model) {
		model.addAttribute("rt", ReturnUrl);
		return "login";
	}

	// 登录
	@RequestMapping("/login.aspx")
	public String login(Model model,String returnUrl,String username,String password,HttpServletRequest request,
			HttpServletResponse response){
	
		Cookie[] cs1 = request.getCookies();		
		boolean result = false;
		Buyer b = null;
		for(Cookie c:cs1){
			if(c.getName().equals("u123456789")){	
				Buyer buyer = buyerService.selectBuyerById(c.getValue());
				if(buyer!=null){
					if(buyer.getUsername().equals(username)&&buyer.getPassword().equals(password)){
						b = new Buyer();
						b.setId(Long.parseLong(c.getValue()));
						result = true;
						break;
					}
				}
				
			}
		}
		System.out.println(result+"-----------------"+returnUrl);
		if(result){
			return "redirect:"+returnUrl;
		}else{
			Buyer buyer = buyerService.selectBuyer(username);
			
			if(buyer!=null){
				//加密密码  md5   十六进制    加盐
				password = encodePassword(password);
				if(buyer.getPassword().equals(password)){
					//登陆成功
					//检查cookie中有没有购物车信息
					Cookie[] cs = request.getCookies();
					for (Cookie cookie : cs) {
						if(cookie.getName().equals("g123456789")){
							String str = buyerService.getStrFromRedis(String.valueOf(buyer.getId()));
							if(str==null){
								buyerService.setStrFromRedis(String.valueOf(buyer.getId()),  cookie.getValue());
							}else{
								buyerService.setStrFromRedis(String.valueOf(buyer.getId()),  str+"-"+cookie.getValue());
							}
							cookie.setMaxAge(0);
							response.addCookie(cookie);
							break;
						}
					}
					//
					Cookie c = new Cookie("u123456789", String.valueOf(buyer.getId()));
					response.addCookie(c);
					return "redirect:"+returnUrl;
				}
				
			}

		}

		model.addAttribute("error", "用户名或密码错误！！！");
		return "login";
		
	}
	
	//加密  （密码过于简单）有规则密码 
			public String encodePassword(String password){
				//
				//password = "gxzcwefxcbergfd123456errttyyytytrnfzeczxcvertwqqcxvxb";
				//1:MD5  算法
				String algorithm = "MD5";
				char[] encodeHex = null;
				try {
					//MD5加密
					MessageDigest instance = MessageDigest.getInstance(algorithm);
					//加密后  
					byte[] digest = instance.digest(password.getBytes());
					
					//
					//2:十六进制
					encodeHex = Hex.encodeHex(digest);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new String(encodeHex);
			}
	
}
