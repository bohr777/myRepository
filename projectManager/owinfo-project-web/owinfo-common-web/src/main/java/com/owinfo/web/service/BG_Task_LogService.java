package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Bruce on 2017/12/19.
 */
@FeignClient("owinfo-common-service")
public interface BG_Task_LogService {
    @RequestMapping(value = "BG_Task_Log/findRecord", method = RequestMethod.POST)
    JSONArray findRecord(JSONObject jsonObject);

    @RequestMapping(value = "BG_Task_Log/findTaskId", method = RequestMethod.POST)
    JSONArray findTaskId(JSONObject jsonObject);

    @RequestMapping(value = "BG_Task_Log/saveLog", method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);

    @RequestMapping(value = "BG_Task_Log/updateByGUID", method = RequestMethod.POST)
    boolean updateByGUID(JSONObject jsonObject);
}
