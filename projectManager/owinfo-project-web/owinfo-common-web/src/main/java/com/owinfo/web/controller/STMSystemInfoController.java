package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.STMSystemInfoService;
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
 * author: qiyong
 * 2018/8/21 15:33
 */
@RestController
@RequestMapping("/STMSystemInfo")
public class STMSystemInfoController {

    private static final Logger logger = LoggerFactory.getLogger(STMSystemInfoController.class);

    @Autowired
    private STMSystemInfoService stmSystemInfoService;

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject){
        jsonObject.put("id", UUID.randomUUID().toString());
        jsonObject.put("createTime", new Date());
        jsonObject.put("finalModify", new Date());
        jsonObject.put("isDelete","0");
        return ResultGenerator.genSuccessResult(stmSystemInfoService.save(jsonObject));
    }
}
