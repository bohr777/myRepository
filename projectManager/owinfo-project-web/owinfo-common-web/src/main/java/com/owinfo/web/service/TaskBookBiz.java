package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/13.
 */
@FeignClient(value = "owinfo-common-service")
public interface TaskBookBiz {
    @RequestMapping(value = "/taskbook/whollySave",method = RequestMethod.POST)
    boolean whollySave(JSONObject jsonObject);
    @RequestMapping(value = "/taskbook/whollyUpdate",method = RequestMethod.POST)
    boolean whollyUpdate(JSONObject jsonObject);
    @RequestMapping(value = "/taskbook/whollyDelete",method = RequestMethod.POST)
    boolean whollyDelete(JSONObject jsonObject);
    @RequestMapping(value = "/taskbook/whollyGet",method = RequestMethod.POST)
    JSONObject whollyGet(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/tempSave",method = RequestMethod.POST)
    boolean tempSave(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/deskGet",method = RequestMethod.POST)
    JSONObject deskGet(JSONObject jsonObject);
}
