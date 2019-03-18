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
public interface SystemsService {
    @RequestMapping(value = "/systems/save",method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);
    @RequestMapping(value = "/systems/update",method = RequestMethod.POST)
    boolean update(JSONObject jsonObject);
    @RequestMapping(value = "/systems/getById",method = RequestMethod.POST)
    JSONObject getById(JSONObject jsonObject);
    @RequestMapping(value = "/systems/deleteByIds",method = RequestMethod.POST)
    boolean deleteByIds(JSONObject jsonObject);
    @RequestMapping(value = "/systems/listForAll",method = RequestMethod.POST)
    JSONObject listForAll();
    @RequestMapping(value="/systems/listByTaskBookId",method= RequestMethod.POST)
    JSONArray listByTaskBookId(JSONObject jsonObject);


    @RequestMapping(value = "/systems/selectSystemLikeSystemName", method = RequestMethod.POST)
    public JSONObject selectSystemLikeSystemName(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systems/selectSystemById", method = RequestMethod.POST)
    public JSONObject selectSystemById(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systems/selectSystemBySystemName", method = RequestMethod.POST)
    public JSONObject selectSystemBySystemName(@RequestBody JSONObject jsonObject);
}
