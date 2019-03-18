package com.owinfo.web.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liyue on 2017/10/17.
 */
@FeignClient(value = "owinfo-common-service")
public interface TaskBookService {
    @RequestMapping(value = "/taskbook/save", method = RequestMethod.POST)
    boolean save(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/update", method = RequestMethod.POST)
    boolean update(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/getById1", method = RequestMethod.POST)
    JSONObject getById(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/deleteByIds", method = RequestMethod.POST)
    boolean deleteByIds(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/listForAll", method = RequestMethod.POST)
    JSONObject listForAll(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/checkNum", method = RequestMethod.POST)
    boolean checkNum(JSONObject jsonObject);


    @RequestMapping(value = "/taskbook/taskBookResult", method = RequestMethod.POST)
    JSONObject taskBookResult(JSONObject jsonObject);

    @RequestMapping(value = "/taskbook/selectTaskbookByProjectName", method = RequestMethod.POST)
    JSONObject selectTaskbookByProjectName(@RequestBody JSONObject jsonObject);
}
