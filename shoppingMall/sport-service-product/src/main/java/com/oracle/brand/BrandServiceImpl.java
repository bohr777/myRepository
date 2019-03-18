package com.oracle.brand;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.mapper.BrandMapper;
import com.oracle.pojo.Brand;
import com.oracle.pojo.Product;
import com.oracle.pojo.Sku;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private HttpSolrServer solrServer;

	public List<Brand> selectBrandList() {

		return brandMapper.selectBrandList();
	}

	public Boolean deleteByPrimaryKey(Long id) {
		int i = brandMapper.deleteByPrimaryKey(id);
		return i == 0 ? false : true;
	}

	public Boolean insert(Brand record) {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean insertSelective(Brand record) {
		int i = brandMapper.insertSelective(record);

		return i == 0 ? false : true;
	}

	public Brand selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return brandMapper.selectByPrimaryKey(id);
	}

	public Boolean updateByPrimaryKeySelective(Brand record) {
		int i = brandMapper.updateByPrimaryKeySelective(record);
//		// 将商品信息存入solr
//		SolrInputDocument sid = new SolrInputDocument();
//		sid.setField("BrandId", record.getId());
//		sid.setField("name_ik", record.getName());
//
//		try {
//			solrServer.add(sid);
//		} catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try {
//			solrServer.commit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return i == 0 ? false : true;
	}

	public List<Brand> selectAllBrand(Brand brand) {

		return brandMapper.selectAllBrand(brand);
	}

}
