package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liyue on 2017/10/17.
 */
@FeignClient(value = "owinfo-common-service")
public interface BaseUserService {
    @RequestMapping(value = "/bservice/baseUserById", method = RequestMethod.POST)
    JSONArray baseUserByInfo(JSONObject jsonObject);

    @RequestMapping(value = "/bservice/getTree", method = RequestMethod.POST)
    JSONArray getTree(JSONObject jsonObject);

    @RequestMapping(value = "/bservice/searchInTree", method = RequestMethod.POST)
    JSONArray listByName(JSONObject jsonObject);

    @RequestMapping(value = "/bservice/selectTaskbookByProjectName", method = RequestMethod.POST)
    JSONObject selectTaskbookByProjectName(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/bservice/selectUserByGUID", method = RequestMethod.POST)
    JSONObject selectUserByGUID(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/bservice/selectUserPathByGUID", method = RequestMethod.POST)
    JSONObject selectUserPathByGUID(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/bservice/selectUserById", method = RequestMethod.POST)
    JSONObject selectUserById(@RequestBody JSONObject jsonObject);
}
