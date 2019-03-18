package com.oracle.controller.sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;
import com.oracle.sku.SkuService;

@Controller
public class SkuController {

	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/updateSku.do")
	public String updateSku(Sku sku) {
		skuService.updateByPrimaryKeySelective(sku);
		return "redirect:skuList.do?productId="+sku.getProductId();
	}
	
	
}
