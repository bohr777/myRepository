package com.oracle.sku;

import java.util.List;

import com.oracle.pojo.Sku;

public interface SkuService {
	int deleteByPrimaryKey(Long id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
    
    List<Sku> selectByProductId(Long productId);
}
