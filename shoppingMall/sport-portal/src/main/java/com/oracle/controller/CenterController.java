package com.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.portal.PortalService;

@Controller
public class CenterController {

	@Autowired
	private PortalService portalService;
	
	@RequestMapping("/")
	public String index(){
		portalService.test();
		return "index";
	}
}
