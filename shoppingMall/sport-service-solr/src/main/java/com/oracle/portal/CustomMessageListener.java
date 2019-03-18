package com.oracle.portal;



import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.mapper.ProductMapper;
import com.oracle.mapper.SkuMapper;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;


public class CustomMessageListener implements MessageListener{

	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private HttpSolrServer solrServer;
	
	
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ActiveMQTextMessage am = (ActiveMQTextMessage)message;
		try {
			System.out.println("ActiveMq中的消息是solr:" + am.getText());
			//保存商品信息到Solr服务器
			//查询库存信息
			Long id = Long.parseLong(am.getText());
			List<Sku> skus = skuMapper.selectByProductId(id);
			float minPrice = skus.get(0).getPrice();
			for (Sku sku : skus) {
				if(sku.getPrice()<minPrice){
					minPrice = sku.getPrice();
				}
			}
			Product product = productMapper.selectByPrimaryKey(id);
			product.setPrice(minPrice);
			product.setImgUrls(product.getImgUrl().split(","));
			//将商品信息存入solr
			SolrInputDocument sid = new SolrInputDocument();
			sid.setField("id", id);
			sid.setField("brandId", product.getBrandId());
			sid.setField("name_ik", product.getName());
			sid.setField("price", minPrice);
			sid.setField("url", product.getImgUrl().split(",")[0]);
			try {
				solrServer.add(sid);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				solrServer.commit();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
