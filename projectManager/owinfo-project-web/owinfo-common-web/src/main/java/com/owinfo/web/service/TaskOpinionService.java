package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/26.
 */

@FeignClient(value = "owinfo-common-service")
public interface TaskOpinionService {
    @RequestMapping(value = "/taskOpinion/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
    @RequestMapping(value = "/taskOpinion/update",method = RequestMethod.POST)
    boolean update(JSONObject jsonObject);
    @RequestMapping(value = "/taskOpinion/deleteByIds",method = RequestMethod.POST)
    boolean deleteByIds(JSONObject jsonObject);
    @RequestMapping(value = "/taskOpinion/listByResourceId",method = RequestMethod.POST)
    JSONObject listByResourceId(JSONObject jsonObject);

    @RequestMapping(value = "/taskOpinion/selectOpinionByTaskId", method = RequestMethod.POST)
    JSONObject selectOpinionByTaskId(@RequestBody JSONObject jsonObject);
}
