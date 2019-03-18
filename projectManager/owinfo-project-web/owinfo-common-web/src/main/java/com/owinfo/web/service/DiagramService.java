package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/1/10.
 */

@FeignClient(value = "owinfo-common-service")
public interface DiagramService {
    @RequestMapping(value = "/diagram/plotCount",method = RequestMethod.POST)
    JSONObject plotCount(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/diagram/list",method = RequestMethod.POST)
    JSONObject list(@RequestBody JSONObject jsonObject);
}
