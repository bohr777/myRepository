package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * author: qiyong
 * 2018/8/21 15:35
 */
@FeignClient(value = "owinfo-common-service")
public interface STMSystemInfoService {


    @RequestMapping(value ="/STMSystemInfo/save", method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
}
