package com.oracle.boot.orderMeal.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.oracle.boot.orderMeal.dataObject.Category;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.service.CategoryService;
import com.oracle.boot.orderMeal.service.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("productlist")
	public ModelAndView productlist(Product product, Integer pageNo){
		if (product.getSize() == null) {
			product.setSize(8);
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		if(product.getPname()!=null&&"".equals(product.getPname())){
			product.setPname(null);
		}
		if(product.getCtype()!=null&&"".equals(product.getCtype())){
			product.setCtype(null);
		}
		Pagination page = productService.findAllProduct(product,pageNo);
		List pList = page.getList();
		for (Object product2 : pList) {
			Product product1 = (Product)product2;
			Category category = categoryService.findByCtype(product1.getCtype());
			product1.setCname(category.getCname());
		}
		ModelAndView mav = new ModelAndView("seller/product","page",page);
		mav.addObject("clist",categoryService.findAll());
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping("toAddProduct")
	public ModelAndView toAddProduct(){
		return new ModelAndView("seller/product-add","clist",categoryService.findAll());
		
	}
	
	@RequestMapping("addProduct")
	public String addProduct(Product product,MultipartFile file,
            HttpServletRequest request) throws Exception{
		
		
		 String fileName = file.getOriginalFilename();
		 String filePath = request.getSession().getServletContext().getRealPath("uploadimg");
	     System.out.println("9999999999999"+filePath);
		 file.transferTo(new File(filePath+File.separator+fileName));
	     product.setPimg("http://localhost:8080/uploadimg/"+fileName);

		productService.save(product);
		return "redirect:productlist";
	}
	
	@RequestMapping("delProduct")
	public String delProduct(Integer pid){
		productService.delete(productService.findById(pid));
		return "redirect:productlist";
	}
	
	@RequestMapping("loadProduct")
	public ModelAndView loadProduct(Integer pid){
		Product product = productService.findById(pid);
		ModelAndView mav = new ModelAndView("seller/product-update","product",product);
		mav.addObject("clist",categoryService.findAll());
		return mav;
	}
	
	@RequestMapping("updateProduct")
	public String updateProduct(Product product){
		productService.update(product);
		return "redirect:productlist";
	}
	
	
}
