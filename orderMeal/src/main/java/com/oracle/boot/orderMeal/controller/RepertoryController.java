package com.oracle.boot.orderMeal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.boot.orderMeal.dao.RepertoryRepository;
import com.oracle.boot.orderMeal.dataObject.Repertory;
import com.oracle.boot.orderMeal.service.RepertoryService;

@Controller
public class RepertoryController {

	@Autowired
	private RepertoryService repertoryService;
	
	@RequestMapping("repertoryList")
	public ModelAndView repertoryList(Integer pid){
		ModelAndView mav = new ModelAndView("seller/repertory","rlist",repertoryService.findByPid(pid));
		return mav;
	}
	
	@RequestMapping("updateRepertory")
	public String updateRepertory(Integer rid,Integer rcount){
		Repertory repertory = repertoryService.findById(rid);
		repertory.setRcount(rcount);
		repertoryService.update(repertory);
		return "repertoryList?pid="+repertory.getPid();
	}
}
