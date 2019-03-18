package com.oracle.cart;

import java.util.Map;

import com.oracle.pojo.Color;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;

public interface CartService {

	public void saveCartToRedis(String str,String uid);
	
	public String getCartFromRedis(String uid);
	
	public Sku selectSku(Long id);
	
	public Product selectProduct(Long id);
	
	public Color selectColor(Long id);
}
