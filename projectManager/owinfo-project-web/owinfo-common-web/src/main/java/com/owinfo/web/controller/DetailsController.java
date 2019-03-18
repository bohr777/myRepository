package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.DetailsService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * Created by liyue on 2017/10/14.
 */
@RestController
@RequestMapping("/details")
public class DetailsController {

    private static final Logger logger = LoggerFactory.getLogger(DetailsController.class);

    @Autowired
    private DetailsService detailsService;

    @RequestMapping(value = "/findDetailByDetailId", method = RequestMethod.POST)
    public Result findDetailByDetailId(@RequestBody JSONObject jsonObject){
        return ResultGenerator.genSuccessResult(detailsService.findDetailByDetailId(jsonObject));
    }


    //根据systemId查询该系统的总条数
    @RequestMapping(value = "/findSystemCountBySystemName", method = RequestMethod.POST)
    public Result findSystemCountBySystemName(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(detailsService.findSystemCountBySystemName(jsonObject));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject) {
        jsonObject.put("id", UUID.randomUUID().toString());
        jsonObject.put("createTime", new Date());
        jsonObject.put("finalModify", new Date());
        jsonObject.put("isDelete", "0");
        return ResultGenerator.genSuccessResult(detailsService.save(jsonObject));

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody JSONObject jsonObject) {
        jsonObject.put("finalModify", new Date());
        return ResultGenerator.genSuccessResult(detailsService.update(jsonObject));
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(detailsService.deleteByIds(jsonObject));
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public Result getById(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(detailsService.getById(jsonObject));
    }

    @RequestMapping(value = "/listByTaskBookId", method = RequestMethod.POST)
    public Result listByTaskBookId(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(detailsService.listByTaskBookId(jsonObject));
    }

    @RequestMapping(value = "checkNum", method = RequestMethod.POST)
    public boolean checkNum(@RequestBody JSONObject jsonObject) {
        try {
            if (jsonObject.has("detailsNum")) {
                return detailsService.checkNum(jsonObject);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "findDetailsLikeDetailsName", method = RequestMethod.POST)
    public Result findDetailsLikeDetailsName(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = detailsService.findDetailsLikeDetailsName(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "findDetailsLikeVersion", method = RequestMethod.POST)
    public Result findDetailsLikeVersion(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = detailsService.findDetailsLikeVersion(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}
