package com.oracle.buyer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.mapper.BuyerMapper;
import com.oracle.pojo.Buyer;

import redis.clients.jedis.Jedis;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private BuyerMapper buyerMapper;
	@Autowired
	private Jedis jedis;

	public Buyer selectBuyer(String username) {
		return buyerMapper.selectBuyer(username);
	}
	
	public String getStrFromRedis(String id) {
		// TODO Auto-generated method stub
		return jedis.get(id);
	}
	
	public void setStrFromRedis(String id, String value) {
		jedis.set(id, value);
		
	}
	
	public Buyer selectBuyerById(String id) {
		// TODO Auto-generated method stub
		return buyerMapper.selectByPrimaryKey(Long.parseLong(id));
	}

	
}