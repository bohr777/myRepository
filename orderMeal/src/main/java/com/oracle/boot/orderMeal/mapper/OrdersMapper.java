package com.oracle.boot.orderMeal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.oracle.boot.orderMeal.dataObject.Orders;
import com.oracle.boot.orderMeal.dataObject.Product;

@Mapper
public interface OrdersMapper {
	
	@SelectProvider(type=OrdersDaoProvider.class,method="findAllOrders")
	public List<Orders> findAllOrders(Orders orders);
	
	class OrdersDaoProvider {
		public String findAllOrders(Orders orders){
			String sql = "select * from orders where 1=1 ";
			sql += " limit #{currentPage},#{size}";
			return sql;
		}
	}
	
	@SelectProvider(type=OrdersCountProvider.class,method="findOrdersCount")
	public int findOrdersCount(Orders orders);
	
	class OrdersCountProvider {
		public String findOrdersCount(Orders orders){
			String sql = "select count(*) from orders where 1=1 ";
			return sql;
		}
	}
	
	
	
}
