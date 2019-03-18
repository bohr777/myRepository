package com.oracle.boot.orderMeal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dataObject.Category;
import com.oracle.boot.orderMeal.service.CategoryService;
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("categorylist")
	public ModelAndView categorylist(){
		return new ModelAndView("seller/category","clist",categoryService.findAll());
	}
	
	@RequestMapping("toAddCategory")
	public String toAddCategory(){
		return "seller/category-add";
	}
	
	@RequestMapping("AddCategory")
	public String AddCategory(Category category){
//		yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		category.setCcreatetime(sdf.format(new Date()));
		category.setCupdatetime(sdf.format(new Date()));
//		category.setCategoryid(122);
		categoryService.save(category);
		System.out.println(category.getCategoryid()+"------------");
		return "redirect:categorylist";
	}
	
	@RequestMapping("DelCategory")
	public String DelCategory(Integer categoryid){
		Category category = categoryService.findById(categoryid);
		categoryService.delete(category);
		return "redirect:categorylist";
	}
	
	@RequestMapping("loadCategory")
	public ModelAndView loadCategory(Integer categoryid){
		return new ModelAndView("seller/category-update","category",categoryService.findById(categoryid));
	}
	
	@RequestMapping("updateCategory")
	public String updateCategory(Category category){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		category.setCcreatetime(sdf.format(new Date()));
		category.setCupdatetime(sdf.format(new Date()));
		categoryService.update(category);
		return "redirect:categorylist";
	}
}
