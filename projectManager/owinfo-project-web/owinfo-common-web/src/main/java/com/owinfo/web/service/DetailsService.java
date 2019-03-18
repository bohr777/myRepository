package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/12/14.
 */

@FeignClient(value = "owinfo-common-service")
public interface DetailsService {
    @RequestMapping(value = "/details/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
    @RequestMapping(value = "/details/update",method = RequestMethod.POST)
    boolean update(JSONObject jsonObject);
    @RequestMapping(value = "/details/getById",method = RequestMethod.POST)
    JSONObject getById(JSONObject jsonObject);
    @RequestMapping(value = "/details/deleteByIds",method = RequestMethod.POST)
    boolean deleteByIds(JSONObject jsonObject);
    @RequestMapping(value="/details/listByTaskBookId",method= RequestMethod.POST)
    JSONArray listByTaskBookId(JSONObject jsonObject);
    @RequestMapping(value = "/details/checkNum",method = RequestMethod.POST)
    boolean checkNum(JSONObject jsonObject);
    @RequestMapping(value = "/details/findSystemCountBySystemName", method = RequestMethod.POST)
    JSONObject findSystemCountBySystemName(JSONObject jsonObject);

    @RequestMapping(value = "/details/findDetailsLikeDetailsName", method = RequestMethod.POST)
    JSONObject findDetailsLikeDetailsName(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/details/findDetailsLikeVersion", method = RequestMethod.POST)
    JSONObject findDetailsLikeVersion(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/details/findDetailByDetailId", method = RequestMethod.POST)
    public JSONObject findDetailByDetailId(@RequestBody JSONObject jsonObject);
}
