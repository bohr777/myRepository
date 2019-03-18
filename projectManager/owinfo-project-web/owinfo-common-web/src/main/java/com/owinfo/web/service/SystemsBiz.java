package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/14.
 */
@FeignClient(value = "owinfo-common-service")
public interface SystemsBiz {
    @RequestMapping(value = "/systems/whollySave",method = RequestMethod.POST)
    boolean whollySave(JSONObject jsonObject);
    @RequestMapping(value = "/systems/whollyUpdate",method = RequestMethod.POST)
    boolean whollyUpdate(JSONObject jsonObject);
    @RequestMapping(value = "/systems/whollyDelete",method = RequestMethod.POST)
    boolean whollyDelete(JSONObject jsonObject);
}
