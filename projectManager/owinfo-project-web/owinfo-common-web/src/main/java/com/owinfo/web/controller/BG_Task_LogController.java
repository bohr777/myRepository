package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.BG_Task_LogService;
import com.owinfo.web.service.BaseUserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * Created by Bruce on 2017/12/19.
 */
@RestController
@RequestMapping("/BG_Task_Log")
public class BG_Task_LogController {

    private static final Logger logger = LoggerFactory.getLogger(BG_Task_LogController.class);

    @Autowired
    private BG_Task_LogService BG_Task_Log;
    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping(value = "/findRecord", method = RequestMethod.POST)
    public Result findRecord(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(BG_Task_Log.findRecord(jsonObject));
    }

    @RequestMapping(value = "/findTaskId", method = RequestMethod.POST)
    public Result findTaskId(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(BG_Task_Log.findTaskId(jsonObject));
    }

    @RequestMapping(value = "/deskSave", method = RequestMethod.POST)
    public Result deskSave(@RequestBody JSONObject jsonObject) {

        ThrInOneEntity entity = new ThrInOneEntity();
        if (jsonObject.getString("username") != null && !"".equals(jsonObject.getString("username"))) {
            entity.setUuid(jsonObject.getString("username"));
        }
        String uuid = entity.getUuid();
        JSONObject user = baseUserService.selectUserById(JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}"));
        entity.setUserName(user.getString("userName"));
        entity.setAllPathName(user.getString("userPath"));
        if (null != entity) {
            jsonObject.put("userid", entity.getUuid());
            jsonObject.put("username", entity.getUserName());
            jsonObject.put("userfullpath", entity.getAllPathName());
        }
        return ResultGenerator.genSuccessResult(BG_Task_Log.save(jsonObject));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject) {
        HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
        ThrInOneEntity entity = new ThrInOneEntity();
        if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
            jsonObject.put("userid", entity.getUuid());
            jsonObject.put("username", entity.getUserName());
            jsonObject.put("userfullpath", entity.getAllPathName());
        }
        return ResultGenerator.genSuccessResult(BG_Task_Log.save(jsonObject));
    }

    @RequestMapping(value = "/deskUpdateByGUID", method = RequestMethod.POST)
    public Result deskUpdateByGUID(@RequestBody JSONObject jsonObject) {
        ThrInOneEntity entity = new ThrInOneEntity();
        if (jsonObject.getString("username") != null && !"".equals(jsonObject.getString("username"))) {
            entity.setUuid(jsonObject.getString("username"));
        }
        String uuid = entity.getUuid();
        JSONObject user = baseUserService.selectUserById(JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}"));
        entity.setUserName(user.getString("userName"));
        entity.setAllPathName(user.getString("userPath"));
        if (null != entity) {
            jsonObject.put("userid", entity.getUuid());
            jsonObject.put("username", entity.getUserName());
            jsonObject.put("userfullpath", entity.getAllPathName());
        }
        return ResultGenerator.genSuccessResult(BG_Task_Log.updateByGUID(jsonObject));
    }

    @RequestMapping(value = "/updateByGUID", method = RequestMethod.POST)
    public Result updateByGUID(@RequestBody JSONObject jsonObject) {
        HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
        ThrInOneEntity entity = new ThrInOneEntity();
        if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
            jsonObject.put("userid", entity.getUuid());
            jsonObject.put("username", entity.getUserName());
            jsonObject.put("userfullpath", entity.getAllPathName());
        }
        return ResultGenerator.genSuccessResult(BG_Task_Log.updateByGUID(jsonObject));
    }
}