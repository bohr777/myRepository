package com.oracle.product;

import java.util.List;

import com.oracle.pojo.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {

	Pagination selectProductList(Product record, int pageNo);

	Boolean insertSelective(Product product);

	Product selectByPrimaryKey(Long id);

	Boolean updateByPrimaryKeySelective(Product record);

	Boolean softDelProductById(Long id);

	Boolean updateProductDels(Long[] id);

	Boolean updateIsShow0(Long[] id);

	Boolean updateIsShow1(Long[] ids);
}
