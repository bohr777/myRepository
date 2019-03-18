package com.oracle.buyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.pojo.Buyer;

public interface BuyerService {

	public Buyer selectBuyer(String username);

	public String getStrFromRedis(String id);

	public void setStrFromRedis(String id, String value);

	public Buyer selectBuyerById(String id);
}
