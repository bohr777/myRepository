package com.oracle.sku;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.mapper.SkuMapper;
import com.oracle.pojo.Sku;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {

	@Autowired
	private SkuMapper skuMapper;
	

	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int insert(Sku record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int insertSelective(Sku record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Sku selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int updateByPrimaryKeySelective(Sku record) {
		int i = skuMapper.updateByPrimaryKeySelective(record);
		return i;
	}

	
	public int updateByPrimaryKey(Sku record) {
		int i = skuMapper.updateByPrimaryKeySelective(record);
		return 0;
	}

	
	public List<Sku> selectByProductId(Long productId) {
		
		return skuMapper.selectByProductId(productId);
	}

}
