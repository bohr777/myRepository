package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.TaskOpinionService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by liyue on 2017/10/14.
 */
@RestController
@RequestMapping("/taskOpinion")
public class TaskOpinionController {

    private static final Logger logger = LoggerFactory.getLogger(TaskOpinionController.class);

    @Autowired
    private TaskOpinionService taskOpinionService;

    @RequestMapping(value= "/save",method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject) {
        //读取session
        jsonObject.put("id", UUID.randomUUID().toString());
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("opinionerId", entity.getUuid());
                jsonObject.put("opinionerName", entity.getUserName());
                jsonObject.put("opinionerFullpath", entity.getAllPathName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
        return ResultGenerator.genSuccessResult(taskOpinionService.save(jsonObject));
    }
    @RequestMapping(value = "/deleteByIds",method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(taskOpinionService.deleteByIds(jsonObject));
    }
    @RequestMapping(value="/listByResourceId",method= RequestMethod.POST)
    public Result listByResourceId(@RequestBody JSONObject jsonObject){
        return ResultGenerator.genSuccessResult(taskOpinionService.listByResourceId(jsonObject));
    }
}
