package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.service.PB_ProjectBaseService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Bruce on 2017/12/14.
 */
@RestController
@RequestMapping("/PB_ProjectBase")
public class PB_ProjectBaseController {

    private static final Logger logger = LoggerFactory.getLogger(PB_ProjectBaseController.class);

    @Autowired
    private PB_ProjectBaseService pb_projectbaserice;
    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping(value = "/PB_ProjectBase", method = RequestMethod.POST)
    public Result queryProject(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            String systemLevel = null;
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            JSONObject systemLevelResult = null;
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
                jsonObject.put("id", entity.getUuid());
                systemLevelResult = baseUserService.selectUserPathByGUID(jsonObject);
            }
            jsonObject.put("followId", follow);
            if ("Administration".equals(systemLevelResult.getString("systemLevel"))) {
                //署级
                systemLevel = "Administration";
            } else if ("Customhouse".equals(systemLevelResult.getString("systemLevel"))){
                //关级
                systemLevel = "Customhouse";
            }else if ("double".equals(systemLevelResult.getString("systemLevel"))){
                //是署级也是关级
                systemLevel = "double";
            }
            jsonObject.put("systemLevel", systemLevel);
            return ResultGenerator.genSuccessResult(pb_projectbaserice.queryProject(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    public Result getTree(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
            }
            jsonObject.put("followId", follow);
            return ResultGenerator.genSuccessResult(pb_projectbaserice.getTree(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/listByName", method = RequestMethod.POST)
    public Result listByName(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
            }
            jsonObject.put("followId", follow);
            return ResultGenerator.genSuccessResult(pb_projectbaserice.searchInTree(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }
}
