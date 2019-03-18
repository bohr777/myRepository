package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Bruce on 2017/12/14.
 */
@FeignClient(value = "owinfo-common-service")
public interface PB_ProjectBaseService {
    @RequestMapping(value = "PB_ProjectBase/queryProject", method = RequestMethod.POST)
    JSONArray queryProject(JSONObject jsonObject);
    @RequestMapping(value = "PB_ProjectBase/getTree",method = RequestMethod.POST)
    JSONArray getTree(JSONObject jsonObject);
    @RequestMapping(value = "PB_ProjectBase/searchInTree",method = RequestMethod.POST)
    JSONArray searchInTree(JSONObject jsonObject);
}
