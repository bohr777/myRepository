package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.SystemsBiz;
import com.owinfo.web.service.SystemsService;
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
 * Created by Administrator on 2017/12/14.
 */

@RestController
@RequestMapping("/systems")
public class SystemsController {

    private static final Logger logger = LoggerFactory.getLogger(SystemsController.class);

    @Autowired
    private SystemsService systemsService;
    @Autowired
    private SystemsBiz systemsBiz;

    @RequestMapping(value = "selectSystemBySystemName",method = RequestMethod.POST)
    public Result selectSystemBySystemName(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult(systemsService.selectSystemBySystemName(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "selectSystemById",method = RequestMethod.POST)
    public Result selectSystemById(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult(systemsService.selectSystemById(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }


//    @RequestMapping(value = "selectSystemLikeSystemName",method = RequestMethod.POST)
//    public Result selectSystemLikeSystemName(@RequestBody JSONObject jsonObject){
//        try{
//            return ResultGenerator.genSuccessResult(systemsService.selectSystemLikeSystemName(jsonObject));
//        }catch (Exception e){
//            return null;
//        }
//    }


    @RequestMapping(value = "whollyDelete",method = RequestMethod.POST)
    public Result whollyDelete(@RequestBody JSONObject jsonObject){
        return ResultGenerator.genSuccessResult(systemsBiz.whollyDelete(jsonObject));
    }
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject){
        jsonObject.put("id", UUID.randomUUID().toString());
        jsonObject.put("createTime", new Date());
        jsonObject.put("finalModify", new Date());
        jsonObject.put("isDelete","0");
        return ResultGenerator.genSuccessResult(systemsService.save(jsonObject));
    }
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result update(@RequestBody JSONObject jsonObject){
        jsonObject.put("finalModify",new Date());
        return ResultGenerator.genSuccessResult(systemsService.update(jsonObject));
    }
    @RequestMapping(value = "systemFormById",method = RequestMethod.POST)
    public Result systemFormById(){
        return ResultGenerator.genSuccessResult();
    }
}
