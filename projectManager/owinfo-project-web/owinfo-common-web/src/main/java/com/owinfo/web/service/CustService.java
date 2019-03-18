package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liyue on 2017/10/17.
 */
@FeignClient(value = "owinfo-common-service")
public interface CustService
{
    @RequestMapping(value = "/cservice/custByInfo",method = RequestMethod.POST)
    JSONArray custByInfo(JSONObject jsonObject);
    @RequestMapping(value = "/cservice/getTree",method = RequestMethod.POST)
    JSONArray getTree(JSONObject jsonObject);
    @RequestMapping(value = "/cservice/searchInTree",method = RequestMethod.POST)
    JSONArray listByName(JSONObject jsonObject);
}
