package com.oracle.controller.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.brand.BrandService;
import com.oracle.pojo.Brand;

@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/selectAllBrand.do")
	public String selectAllBrand(Brand brand,Model model){
		if (brand.getIsDisplay() != null && brand.getIsDisplay() == 2) {
			brand.setIsDisplay(null);
		}
		model.addAttribute("blist", brandService.selectAllBrand(brand));
		model.addAttribute("brand", brand);
		return "brand/list";
	}
	
	@RequestMapping("/brandAdd.do")
	public String brandAdd(Brand brand){
		brandService.insertSelective(brand);
		return "redirect:selectAllBrand.do";
	}
	
	@RequestMapping("/delBrand.do")
	public String delBrand(Long id){
		brandService.deleteByPrimaryKey(id);
		return "redirect:selectAllBrand.do";
	}
	
	@RequestMapping("/brandUpdate.do")
	public String brandUpdate(Brand brand){
		brandService.updateByPrimaryKeySelective(brand);
		return "redirect:selectAllBrand.do";
	}
}
