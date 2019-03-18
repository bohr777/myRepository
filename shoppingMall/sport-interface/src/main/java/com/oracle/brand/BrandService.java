package com.oracle.brand;

import java.util.List;

import com.oracle.pojo.Brand;

public interface BrandService {
	
	Boolean deleteByPrimaryKey(Long id);

	Boolean insert(Brand record);

	Boolean insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    Boolean updateByPrimaryKeySelective(Brand record);
    
    List<Brand> selectBrandList();
    
    List<Brand> selectAllBrand(Brand brand);
}
