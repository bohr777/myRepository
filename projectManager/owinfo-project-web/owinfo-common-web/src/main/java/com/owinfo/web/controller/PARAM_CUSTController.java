package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.PARAM_CUSTService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Joe on 2017/12/13.
 */
@RestController
@RequestMapping("PARAM_CUST")
public class PARAM_CUSTController {

    private static final Logger logger = LoggerFactory.getLogger(PARAM_CUSTController.class);

    @Autowired
    private PARAM_CUSTService param_custService;

    @RequestMapping("/save")
    public Result save(@RequestBody JSONObject jsonObject){
        return ResultGenerator.genSuccessResult(param_custService.save(jsonObject));

    }
}
