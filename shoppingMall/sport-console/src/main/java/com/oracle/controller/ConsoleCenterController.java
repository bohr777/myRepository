package com.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.brand.BrandService;
import com.oracle.color.ColorService;
import com.oracle.product.ProductService;
import com.oracle.sku.SkuService;
import com.oracle.test.TestService;

@Controller
public class ConsoleCenterController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ColorService colorService;

	@RequestMapping("index.do")
	public String index() {
		return "index";
	}

	@RequestMapping("top.do")
	public String top() {
		return "top";
	}

	@RequestMapping("main.do")
	public String main() {
		return "main";
	}

	@RequestMapping("left.do")
	public String left() {
		return "left";
	}

	@RequestMapping("right.do")
	public String right() {
		return "right";
	}

	@RequestMapping("product_main.do")
	public String productMain() {
		return "frame/product_main";
	}

	@RequestMapping("product_left.do")
	public String productLeft() {
		return "frame/product_left";
	}

	@RequestMapping("product_list.do")
	public String productList() {
		return "frame/product_list";
	}

	@RequestMapping("toProductAdd.do")
	public String toProductAdd(Model model) {
		model.addAttribute("colors", colorService.selectAllColor());
		model.addAttribute("blist", brandService.selectBrandList());
		return "product/add";
	}

	@RequestMapping("toproductUpdate.do")
	public String toproductUpdate(Long id, Integer pageNo, Integer size, Model model) {
		model.addAttribute("product", productService.selectByPrimaryKey(id));
		model.addAttribute("colors", colorService.selectAllColor());
		model.addAttribute("blist", brandService.selectBrandList());
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("size", size);
		return "product/update";
	}

	@RequestMapping("skuList.do")
	public String skuList(Long productId, Model model) {
		
		model.addAttribute("slist", skuService.selectByProductId(productId));
		model.addAttribute("clist", colorService.selectAllColor());
		return "sku/list";
	}
	
	@RequestMapping("toBrandAdd.do")
	public String toBrandAdd() {
		return "brand/add";
	}
	
	@RequestMapping("toBrandtUpdatePage.do")
	public String toBrandtUpdatePage(Long id, Model model) {
		model.addAttribute("brand", brandService.selectByPrimaryKey(id));
//		model.addAttribute("colors", colorService.selectAllColor());
//		model.addAttribute("blist", brandService.selectBrandList());
//		model.addAttribute("pageNo", pageNo);
//		model.addAttribute("size", size);
		return "brand/edit";
	}
	
}
