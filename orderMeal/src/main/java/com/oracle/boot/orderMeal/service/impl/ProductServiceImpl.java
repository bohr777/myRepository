package com.oracle.boot.orderMeal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oracle.boot.orderMeal.dao.ProductRepository;
import com.oracle.boot.orderMeal.dataObject.Product;
import com.oracle.boot.orderMeal.mapper.ProductMapper;
import com.oracle.boot.orderMeal.service.ProductService;

import cn.itcast.common.page.Pagination;
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductMapper productMapper;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	
	public void save(Product product) {
		productRepository.save(product);
	}

	
	public void delete(Product product) {
		productRepository.delete(product);
	}

	
	public void update(Product product) {
		productRepository.save(product);
	}

	
	public Product findById(Integer pid) {
		return productRepository.findById(pid).get();
	}

	public Pagination findAllProduct(Product product,Integer pageNo){
		product.setCurrentPage((pageNo-1)*product.getSize());
		List<Product> plist = productMapper.findAllProduct(product);
		Pagination page = new Pagination(pageNo,product.getSize(),productMapper.findProductCount(product));
		StringBuilder params = new StringBuilder();
		params.append("size=" + product.getSize());
		if(product.getPname()!=null){
			params.append("&pname=" + product.getPname());
		}
		if(product.getCtype()!=null){
			params.append("&ctype=" + product.getCtype());
		}
		String url = "productlist";
		page.setList(plist);
		page.pageView(url, params.toString());
		return page;
	}


	
	public List<Product> findByCtype(Integer ctype) {
		
		return productRepository.findByCtype(ctype);
	}


	@Override
	public Product findByPname(String name) {
		return productRepository.findByPname(name);
	}
 	
	
	
}
