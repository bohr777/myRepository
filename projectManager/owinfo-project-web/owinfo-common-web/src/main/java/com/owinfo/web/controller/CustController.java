package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.CustService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liyue on 2017/10/14.
 */
@RestController
@RequestMapping("/ccontroller")
public class CustController {

    private static final Logger logger = LoggerFactory.getLogger(CustController.class);

    @Autowired
    private CustService custService;

    //模糊查询
    @RequestMapping(value = "/custByInfo", method = RequestMethod.POST)
    public Result getByUser(@RequestBody JSONObject jsonObject) {
        Result result = null;
        if (jsonObject.get("displayName") != null && !jsonObject.get("displayName").equals("")) {
            result = ResultGenerator.genSuccessResult(custService.custByInfo(jsonObject));
        }
        return result;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = null;
        if (jsonObject.has("userId")) {

        }
        return result;
    }

    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    public Result getTree(@RequestBody JSONObject jsonObject) {
        try {
            return ResultGenerator.genSuccessResult(custService.getTree(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }

    @RequestMapping(value = "/listByName", method = RequestMethod.POST)
    public Result listByName(@RequestBody JSONObject jsonObject) {
        try {
            return ResultGenerator.genSuccessResult(custService.listByName(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }
}
