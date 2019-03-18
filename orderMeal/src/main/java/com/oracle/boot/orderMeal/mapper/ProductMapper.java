package com.oracle.boot.orderMeal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.oracle.boot.orderMeal.dataObject.Product;

@Mapper
public interface ProductMapper {

	@SelectProvider(type = ProductDaoProvider.class, method = "findAllProduct")
    public List<Product> findAllProduct(Product product);
	
	class ProductDaoProvider {
        public String findAllProduct(Product product){  
            String sql = "SELECT * FROM product where 1=1 ";  
            if(product.getPname()!=null){  
                sql += "and pname like CONCAT(CONCAT('%',#{pname}),'%')";  
            }
            if(product.getCtype()!=null){
            	sql += "and ctype = #{ctype}"; 
            }
            sql = sql +" limit #{currentPage},#{size}";
            return sql;  
        }  
    }
	 
	@SelectProvider(type = ProductCountProvider.class, method = "findProductCount")
    public int findProductCount(Product product);
	
	class ProductCountProvider {
        public String findProductCount(Product product){  
            String sql = "SELECT count(*) FROM product where 1=1 ";  
            if(product.getPname()!=null){  
                sql += "and pname like CONCAT(CONCAT('%',#{pname}),'%')";  
            }
            if(product.getCtype()!=null){
            	sql += "and ctype = #{ctype}"; 
            }
            return sql;  
        }  
    }
	
}
