package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.TaskOpinionMapper;
import com.owinfo.entity.TaskOpinion;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
@RestController
@RequestMapping("/taskOpinion")
public class TaskOpinionServiceImpl extends AbstractService<TaskOpinion> {

    private static final Logger logger = LoggerFactory.getLogger(TaskOpinionServiceImpl.class);

    @Autowired
    private TaskOpinionMapper taskOpinionMapper;

    @RequestMapping(value = "/selectOpinionByTaskId", method = RequestMethod.POST)
    public JSONObject selectOpinionByTaskId(@RequestBody JSONObject jsonObject) {
        JSONObject result = new JSONObject();
        try {
            String resourceId = jsonObject.getString("resourceId");
            List<TaskOpinion> taskOpinionList = taskOpinionMapper.selectOpinionByTaskId(resourceId);
            if (taskOpinionList.size() > 0) {
                result.put("result", "1");
            } else {
                result.put("result", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public boolean deleteByIds(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray ids = jsonObject.getJSONArray("ids");
            for (int i = 0; i < ids.size(); i++) {
                taskOpinionMapper.deleteByPrimaryKey(ids.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/listByResourceId", method = RequestMethod.POST)
    public JSONArray listByResourceId(@RequestBody JSONObject jsonObject) {
        try {
            List<TaskOpinion> taskOpinions = taskOpinionMapper.listByResourceId(jsonObject.getString("id"));
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            JSONArray jsonArray = JSONArray.fromObject(taskOpinions, jsonConfig);
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }
}
