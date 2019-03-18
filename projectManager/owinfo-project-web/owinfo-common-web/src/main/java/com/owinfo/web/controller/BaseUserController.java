package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.service.CustService;
import net.sf.json.JSONArray;
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
@RequestMapping("/baseController")
public class BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserController.class);

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private CustService custService;

    //模糊查询
    @RequestMapping(value = "/baseUserByInfo", method = RequestMethod.POST)
    public Result getByUser(@RequestBody JSONObject jsonObject) {
        Result result = null;
        if (jsonObject.get("userName") != null && !jsonObject.get("userName").equals("")) {
            result = ResultGenerator.genSuccessResult(baseUserService.baseUserByInfo(jsonObject));
        }
        return result;

    }

    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    public Result getTree(@RequestBody JSONObject jsonObject) {
        try {
            if (jsonObject.has("isSpread")) {
                HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
                ThrInOneEntity entity = (ThrInOneEntity) session.getAttribute("currentUser");
                String[] paths = entity.getAllPathName().split("/");
                JSONObject params = new JSONObject();
                params.put("parent", "");
                params.put("level", 1);
                JSONArray tree = custService.getTree(params);
                params.put("parent", tree.getJSONObject(0).getString("displayName"));
                params.put("level", 2);
                tree.getJSONObject(0).put("children", custService.getTree(params));
                params.put("parent", paths[0] + "\\" + paths[1]);
                params.put("level", 3);
                JSONArray child2 = tree.getJSONObject(0).getJSONArray("children");
                for (int i = 0; i < child2.size(); i++) {
                    JSONObject temp = child2.getJSONObject(i);
                    if (temp.getString("name").equals(paths[1])) {
                        temp.put("children", custService.getTree(params));
                        break;
                    }
                }
                return ResultGenerator.genSuccessResult(tree);
            } else {
                return ResultGenerator.genSuccessResult(baseUserService.getTree(jsonObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }

    @RequestMapping(value = "/listByName", method = RequestMethod.POST)
    public Result listByName(@RequestBody JSONObject jsonObject) {
        try {
            return ResultGenerator.genSuccessResult(baseUserService.listByName(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }
}
