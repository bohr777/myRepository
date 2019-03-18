package com.owinfo.web.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/1/26.
 */
@FeignClient(value = "owinfo-common-service")
public interface SystemInfoService {
    //查询系统名称是存在
    @RequestMapping(value = "/systemInfo/findSystemBySystemName", method = RequestMethod.POST)
    boolean findSystemBySystemName(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/save", method = RequestMethod.POST)
    boolean save(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/update", method = RequestMethod.POST)
    boolean update(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/getById", method = RequestMethod.POST)
    JSONObject getById(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/deleteByIds", method = RequestMethod.POST)
    boolean deleteByIds(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/listForAll", method = RequestMethod.POST)
    JSONObject listForAll(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/getByName", method = RequestMethod.POST)
    JSONObject getByName(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/getTree", method = RequestMethod.POST)
    JSONArray getTree(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/systemInfo/searchInTree", method = RequestMethod.POST)
    JSONArray listByName(@RequestBody JSONObject jsonObject);
}
