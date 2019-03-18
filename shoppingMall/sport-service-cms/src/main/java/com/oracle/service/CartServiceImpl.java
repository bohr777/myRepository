package com.oracle.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.cart.CartService;
import com.oracle.mapper.ColorMapper;
import com.oracle.mapper.ProductMapper;
import com.oracle.mapper.SkuMapper;
import com.oracle.pojo.Color;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;

import redis.clients.jedis.Jedis;
@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private Jedis jedis;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ColorMapper colorMapper;
	
	public void saveCartToRedis(String str,String uid) {
		String s = jedis.get(uid);
		if(s==null){
			s = str;
		}else{
			s = s+"-"+str;
		}
		jedis.set(uid,s);

	}
	
	public String getCartFromRedis(String uid) {
		return jedis.get(uid);
	}

	
	public Sku selectSku(Long id) {
		return skuMapper.selectByPrimaryKey(id);
	}

	
	public Product selectProduct(Long id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public Color selectColor(Long id) {
		return colorMapper.selectByPrimaryKey(id);
	}

}
