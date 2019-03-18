package com.owinfo.activity.core;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/26.
 */

@FeignClient(value = "owinfo-common-service")
public interface TaskInfoService {
    @RequestMapping(value = "/taskInfo/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
    @RequestMapping(value = "/taskInfo/update",method=RequestMethod.POST)
    boolean update(JSONObject jsonObject);
    @RequestMapping(value = "/taskInfo/getById",method = RequestMethod.POST)
    JSONObject getById(JSONObject jsonObject);
    @RequestMapping(value = "/taskInfo/saveOrUpdate",method = RequestMethod.POST)
    boolean saveOrUpdate(JSONObject jsonObject);


    @RequestMapping(value = "/taskInfo/findTaskInfoByActivityId", method = RequestMethod.POST)
    JSONObject findTaskInfoByActivityId(@RequestBody JSONObject jsonObject);
}
