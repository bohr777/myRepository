package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/29.
 */
@FeignClient(value = "owinfo-common-service")
public interface TaskAuthService {
    @RequestMapping(value = "/taskAuth/getByNode",method = RequestMethod.POST)
    JSONObject getByNode(@RequestBody JSONObject jsonObject);
}
