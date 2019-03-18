package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liyue on 2017/10/17.
 */
@FeignClient(value = "owinfo-common-service")
public interface SetService
{
    @RequestMapping(value = "/sservice/getSetUpById",method = RequestMethod.POST)
    JSONObject setById(JSONObject jsonObject);

    @RequestMapping(value = "/sservice/deleteById",method = RequestMethod.POST)
    boolean deleteById(JSONObject jsonObject);

    @RequestMapping(value = "/sservice/setSave",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
}
