package com.owinfo.web.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */
@FeignClient(value = "owinfo-common-service")
public interface DeptService {

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    JSONObject getUser(@RequestBody Map<String, Object> param);
}
