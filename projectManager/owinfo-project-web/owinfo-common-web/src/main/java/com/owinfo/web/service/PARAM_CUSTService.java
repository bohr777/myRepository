package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Joe on 2017/12/13.
 */
@FeignClient(value = "owinfo-common-service")
public interface PARAM_CUSTService {
    @RequestMapping(value = "/PARAM_CUST/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
}
