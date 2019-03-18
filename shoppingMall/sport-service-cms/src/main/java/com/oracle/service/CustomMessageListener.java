package com.oracle.service;



import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.oracle.mapper.ColorMapper;
import com.oracle.mapper.ProductMapper;
import com.oracle.mapper.SkuMapper;
import com.oracle.pojo.Color;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;
import com.oracle.staticPage.StaticPageServiceImpl;

import redis.clients.jedis.Jedis;


public class CustomMessageListener implements MessageListener{

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private Jedis jedis;
	@Autowired
	private HttpSolrServer solrServer;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ColorMapper colorMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private StaticPageServiceImpl staticPageService;
	
	
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ActiveMQTextMessage am = (ActiveMQTextMessage)message;
		try {
			System.out.println("ActiveMq中的消息是solr:" + am.getText());
			//实现静态化
			Long id = Long.parseLong(am.getText());
			Map<String,Object> root = new HashMap<>();
			List<Sku> skus = skuMapper.selectByProductId(id);
			
			 float minPrice = skus.get(0).getPrice();
				 for (Sku sku : skus) {
				 if (sku.getPrice() < minPrice) {
				 minPrice = sku.getPrice();
				 }
				 }
			
			Product product = productMapper.selectByPrimaryKey(id);
			product.setPrice(minPrice);
			product.setImgUrls(product.getImgUrl().split(","));
			 //遍历一次 相同不要
			 Set<Color> colors = new HashSet<>();
			 for (Sku sku : skus) {
			 colors.add(colorMapper.selectByPrimaryKey(sku.getColorId()));
			 }
			 root.put("product", product);
			 root.put("skus", skus);
			 root.put("colors", colors);
			 staticPageService.productStaticPage(root, String.valueOf(id));
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
