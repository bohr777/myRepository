package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.SetService;
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
 * Created by liyue on 2017/10/14.
 */
@RestController
@RequestMapping("/setController")
public class SetController {

    private static final Logger logger = LoggerFactory.getLogger(SetController.class);

    @Autowired
    private SetService setService;

    //查询
    @RequestMapping(value = "/setByInfo", method = RequestMethod.POST)
    public Result getSetById(@RequestBody JSONObject jsonObject) {
        try{
            HttpSession session=SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("userId",entity.getUuid());
            }else{
                return null;
            }
            return ResultGenerator.genSuccessResult(setService.setById(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    //保存
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveSetInfo(@RequestBody JSONObject jsonObject) {
        Result result = null;
        try {
            HttpSession session= SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("userId",entity.getUuid());
                jsonObject.put("founderName", entity.getUserName());
                jsonObject.put("founderFullpath", entity.getAllPathName());
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }else{
                return null;
            }
            setService.deleteById(jsonObject);
            result = ResultGenerator.genSuccessResult(setService.save(jsonObject));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            result = ResultGenerator.genFailResult("登录信息有误，请重新登陆");
            return result;
        }
    }

}
