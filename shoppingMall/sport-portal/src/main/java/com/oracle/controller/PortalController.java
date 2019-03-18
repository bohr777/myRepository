package com.oracle.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.brand.BrandService;
import com.oracle.cart.CartService;
import com.oracle.pojo.Cart;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;
import com.oracle.portal.PortalService;
import com.oracle.sku.SkuService;

@Controller
public class PortalController {

	@Autowired
	private PortalService portalService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CartService cartService;

	@RequestMapping("/productList")
	public ModelAndView selectProductBySolr(String keyword, Integer pageNo, Integer size, Long brandId, String priceStr)
			throws Exception {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (size == null) {
			size = 12;
		}

		ModelAndView mav = new ModelAndView("search", "pagination",
				portalService.selectProductsBySolr(keyword, pageNo, size, brandId, priceStr));
		mav.addObject("brands", brandService.selectBrandList());
		return mav;
	}

	@RequestMapping("/addCart")
	public String addCart(String url, String sid, Integer buyNum, HttpServletRequest request,
			HttpServletResponse response) {
		
		sid = sid.toString().substring(2);
		
		System.out.println("diudiudiu");
		// 判断是否登录
		Cookie[] cs = request.getCookies();
		boolean result = false;
		String uid = null;
		for (Cookie c : cs) {
			if (c.getName().equals("u123456789")) {
				uid = c.getValue();
				result = true;
				break;
			}
		}
		// 没登陆
		if (!result) {
			String s = null;
			for (Cookie c : cs) {
				if (c.getName().equals("g123456789")) {
					s = c.getValue();
				}
			}
			if (s == null) {
				s = sid + "-" + buyNum;
			} else {
				s = s + "-" + sid + "-" + buyNum;
			}
			Cookie cookie = new Cookie("g123456789", s);
			response.addCookie(cookie);
			return "redirect:" + url;
		}
		// 如果登录存到redis中
		cartService.saveCartToRedis(sid + "-" + buyNum, uid);
		return "redirect:" + url;
	}

	// 去购物车
	@RequestMapping("/toCart")
	public String toCart(HttpServletRequest request, Model model) {
		Cookie[] cs = request.getCookies();
		String uid = null;
		boolean result = false;
		for (Cookie c : cs) {
			uid = c.getValue();
			if (c.getName().equals("u123456789")) {
				result = true;
				break;
			}
		}
		// 登录从redis取出
		String[] strs = null;
		if (result) {
			String str = cartService.getCartFromRedis(uid);
			strs = str.split("-");

		} else {// 没登陆从cookie中取
			for (Cookie c : cs) {
				if (c.getName().equals("g123456789")) {
					strs = c.getValue().split("-");
					break;
				}
			}
		}
		//i为商品数量
		int i = 0;
		if (strs != null) {
			i = strs.length / 2;
		} else {

		}
		List<Cart> carts = new ArrayList<>();
		double tm = 0;
		for (int k = 0; k < i; k++) {
			Cart cart = new Cart();
			Sku sku = cartService.selectSku(Long.parseLong(strs[k * 2]));
			Product product = cartService.selectProduct(sku.getProductId());
			product.setImgUrls(product.getImgUrl().split(","));
			cart.setSku(sku);
			cart.setProduct(product);
			cart.setCount(Integer.parseInt(strs[k * 2 + 1]));
			cart.setAmount((double) (Integer.parseInt(strs[k * 2 + 1]) * sku.getPrice()));
			carts.add(cart);
			tm += cart.getAmount();
		}
		

		model.addAttribute("totalmoney", tm);
		model.addAttribute("carts", carts);
		return "cart";
	}

}
