package com.oracle.portal;

import cn.itcast.common.page.Pagination;

public interface PortalService {

	public boolean test();
	
	public  Pagination selectProductsBySolr(String keyword,Integer pageNo,Integer size,Long brandId,String priceStr) throws Exception;
}
