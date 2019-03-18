package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.TaskInfoService;
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
@RequestMapping("/taskInfo")
public class TaskInfoController {

    private static final Logger logger = LoggerFactory.getLogger(TaskInfoController.class);

    @Autowired
    private TaskInfoService taskInfoService;

    @RequestMapping(value= "/save",method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject) {
        //读取session
        return ResultGenerator.genSuccessResult(taskInfoService.save(jsonObject));
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(taskInfoService.update(jsonObject));
    }
}
