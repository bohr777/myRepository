package com.oracle.mapper;

import java.util.List;

import com.oracle.pojo.Brand;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);
    
    List<Brand> selectBrandList();
    
    List<Brand> selectAllBrand(Brand brand);
}