package com.oracle.product;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.mapper.ColorMapper;
import com.oracle.mapper.ProductMapper;
import com.oracle.mapper.SkuMapper;
import com.oracle.pojo.Color;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;
import com.oracle.product.ProductService;
import com.oracle.staticPage.StaticPageServiceImpl;

import cn.itcast.common.page.Paginable;
import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

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
	private StaticPageServiceImpl staticPageService;
	@Autowired
	private JmsTemplate jmsTemplate;

	public Pagination selectProductList(Product record, int current) {

		record.setCurrentPage((current - 1) * record.getSize());
		List<Product> plist = productMapper.selectProductList(record);
		for (Product product : plist) {
			product.setImgUrls(product.getImgUrl().split(","));
		}
		Pagination page = new Pagination(current, record.getSize(), productMapper.selectCount(record));
		StringBuilder params = new StringBuilder();
		params.append("size=" + record.getSize());

		if (record.getName() != null) {
			params.append("&name=" + record.getName());
		}
		if (record.getBrandId() != null) {
			params.append("&brandId=" + record.getBrandId());
		}
		if (record.getIsShow() != null) {
			params.append("&isShow=" + record.getIsShow());
		}

		String url = "productList.do";
		page.setList(plist);
		page.pageView(url, params.toString());
		return page;
	}

	public Boolean insertSelective(Product product) {
		String strImg = "";
		if (product.getImgUrls() != null) {

			for (String img : product.getImgUrls()) {
				strImg += img + ",";
			}
			product.setImgUrl(strImg);
		}
		System.out.println(product.getImgUrls()[0]);
		String strColors = "";
		for (String color : product.getColorss()) {
			strColors += color + ",";
		}
		product.setColors(strColors);

		String strSizes = "";
		for (String size : product.getSizess()) {
			strSizes += size + ",";
		}
		product.setSizes(strSizes);

		if (product.getIsCommend() == null) {
			product.setIsCommend(0);
		}
		if (product.getIsHot() == null) {
			product.setIsHot(0);
		}
		if (product.getIsNew() == null) {
			product.setIsNew(0);
		}
		if (product.getIsCommend() == null) {
			product.setIsCommend(0);
		}
		product.setIsShow(0);
		product.setIsDel(1);
		product.setCreateTime(new Date());
		product.setId(jedis.incr("productId"));
		strImg.substring(0, strImg.length() - 1);
		int i = productMapper.insertSelective(product);
		return i == 0 ? false : true;
	}

	public Product selectByPrimaryKey(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		product.setColorss(product.getColors().split(","));
		product.setSizess(product.getSizes().split(","));
		product.setImgUrls(product.getImgUrl().split(","));
		return product;
	}

	public Boolean updateByPrimaryKeySelective(Product product) {
		String strImg = "";
		if (product.getImgUrls() != null) {

			for (String img : product.getImgUrls()) {
				strImg += img + ",";
			}
			product.setImgUrl(strImg);
		}

		String strColors = "";
		for (String color : product.getColorss()) {
			strColors += color + ",";
		}
		product.setColors(strColors);

		String strSizes = "";
		for (String size : product.getSizess()) {
			strSizes += size + ",";
		}
		product.setSizes(strSizes);

		if (product.getIsCommend() == null) {
			product.setIsCommend(0);
		}
		if (product.getIsHot() == null) {
			product.setIsHot(0);
		}
		if (product.getIsNew() == null) {
			product.setIsNew(0);
		}
		if (product.getIsCommend() == null) {
			product.setIsCommend(0);
		}
		int i = productMapper.updateByPrimaryKeySelective(product);
		return i == 0 ? false : true;
	}

	public Boolean softDelProductById(Long id) {
		int i = productMapper.softDelProductById(id);
		System.out.println("----------");
		return i == 0 ? false : true;
	}

	public Boolean updateProductDels(Long[] id) {
		int i = productMapper.updateProductDels(id);
		return i == id.length ? true : false;
	}

	// 下架
	public Boolean updateIsShow0(Long[] id) {
		int i = productMapper.updateIsShow0(id);
		return i == id.length ? true : false;
	}

	// 上架
	public Boolean updateIsShow1(Long[] ids) {
		int i = productMapper.updateIsShow1(ids);

		for (final Long id : ids) {
			jmsTemplate.send(new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {

					return session.createTextMessage(String.valueOf(id));
				}
			});
		}

		// for (Long id : ids) {
		// List<Sku> skus = skuMapper.selectByProductId(id);
		// float minPrice = skus.get(0).getPrice();
		// for (Sku sku : skus) {
		// if (sku.getPrice() < minPrice) {
		// minPrice = sku.getPrice();
		// }
		// }
		// Product product = productMapper.selectByPrimaryKey(id);
		// product.setPrice(minPrice);
		// product.setImgUrls(product.getImgUrl().split(","));
		// // 将商品信息存入solr
		// SolrInputDocument sid = new SolrInputDocument();
		// sid.setField("id", id);
		// sid.setField("name_ik", product.getName());
		// sid.setField("brandId", product.getBrandId());
		// sid.setField("price", minPrice);
		// sid.setField("url", product.getImgUrl().split(",")[0]);
		// try {
		// solrServer.add(sid);
		// } catch (SolrServerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// //静态化
		// //数据
		// Map<String,Object> root = new HashMap<>();
		//
		// //遍历一次 相同不要
		// Set<Color> colors = new HashSet<>();
		// for (Sku sku : skus) {
		// colors.add(colorMapper.selectByPrimaryKey(sku.getColorId()));
		// }
		// root.put("product", product);
		// root.put("skus", skus);
		// root.put("colors", colors);
		// staticPageService.productStaticPage(root, String.valueOf(id));
		//
		// }
		//
		// try {
		// solrServer.commit();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return i == ids.length ? true : false;
	}

}
