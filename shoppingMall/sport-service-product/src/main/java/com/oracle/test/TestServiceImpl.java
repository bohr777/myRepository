package com.oracle.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.mapper.ProductMapper;
import com.oracle.pojo.Product;

@Service("testService")
public class TestServiceImpl implements TestService{

	@Autowired
	private ProductMapper productMapper;

	public Product test() {
		// TODO Auto-generated method stub
		return productMapper.selectByPrimaryKey(1L);
	}

}
