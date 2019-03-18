package com.oracle.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.brand.BrandService;
import com.oracle.color.ColorService;
import com.oracle.pojo.Product;
import com.oracle.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ColorService colorService;

	@RequestMapping("/productList.do")
	public ModelAndView productList(Product product, Integer pageNo) {
		if (product.getName() != null && product.getName() == "") {
			product.setName(null);
		}
		if (product.getBrandId() != null && product.getBrandId() == 0) {
			product.setBrandId(null);
		}
		if (product.getIsShow() != null && product.getIsShow() == 2) {
			product.setIsShow(null);
		}
		if(product.getSize()==null){
			product.setSize(8);
		}
		if(pageNo==null){
			pageNo=1;
		}

		ModelAndView mav = new ModelAndView("product/list", "page", productService.selectProductList(product, pageNo));
		mav.addObject("blist", brandService.selectBrandList());
		mav.addObject("product", product);
		return mav;
	}

	@RequestMapping("/productAdd.do")
	public String productAdd(Product product) {
		productService.insertSelective(product);
		return "redirect:productList.do?size=8&pageNo=1";
	}

	@RequestMapping("/productUpdate.do")
	public String productUpdate(Product product,Integer pageNo) {
		productService.updateByPrimaryKeySelective(product);
		return "redirect:productList.do?size=8&pageNo="+pageNo;
	}
	
	@RequestMapping("/delProduct.do")
	public String delProduct(Long id,Integer pageNo,Integer size) {
		productService.softDelProductById(id);
		return "redirect:productList.do?size=8&pageNo="+pageNo;
	}
	
	
	@RequestMapping("/updateProductDels.do")
	public String updateProductDels(Long[] ids) {
		productService.updateProductDels(ids);
		return "redirect:productList.do";
	}
	
	@RequestMapping("/updateIsShow0.do")
	public String updateIsShow0(Long[] ids) {
		productService.updateIsShow0(ids);
		return "redirect:productList.do";
	}
	
	@RequestMapping("/updateIsShow1.do")
	public String updateIsShow1(Long[] ids) {
		productService.updateIsShow1(ids);
		return "redirect:productList.do";
	}
}
