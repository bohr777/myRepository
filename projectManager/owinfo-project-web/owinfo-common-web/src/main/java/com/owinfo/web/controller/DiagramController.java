package com.owinfo.web.controller;

import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.service.DiagramService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/10.
 */
@RestController
@RequestMapping("/diagram")
public class DiagramController {

    private static final Logger logger = LoggerFactory.getLogger(DiagramController.class);

    @Autowired
    private DiagramService diagramService;

    @RequestMapping(value = "/stackPlot", method = RequestMethod.POST)
    public Result stackPlot(@RequestBody JSONObject jsonObject) {
        try {
            return ResultGenerator.genSuccessResult(diagramService.plotCount(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult(diagramService.list(jsonObject));
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}
