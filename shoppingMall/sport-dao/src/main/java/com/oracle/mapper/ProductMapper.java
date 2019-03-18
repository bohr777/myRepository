package com.oracle.mapper;

import java.util.List;

import com.oracle.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    List<Product> selectProductList(Product record);
    
    int selectCount(Product record);
    
    int softDelProductById(Long id);
    
    int updateProductDels(Long[] id);
    
    int updateIsShow0(Long[] id);
    
    int updateIsShow1(Long[] id);
}