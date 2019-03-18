package com.owinfo.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.TaskBookSystemInfoMapper;
import com.owinfo.entity.TaskBook;
import com.owinfo.entity.TaskBookSystemInfo;
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

/**
 * author: qiyong
 * 2018/8/21 16:03
 */
@RestController
@RequestMapping("/taskBookSystemInfo")
public class TaskBookSystemInfoImpl  extends AbstractService<TaskBookSystemInfo> {

    private static final Logger logger = LoggerFactory.getLogger(TaskBookSystemInfoImpl.class);

    @Autowired
    private TaskBookSystemInfoMapper taskBookSystemInfoMapper;

    @RequestMapping(value = "selectSystemByTaskbookId", method = RequestMethod.POST)
    public JSONArray selectSystemByTaskbookId(@RequestBody JSONObject jsonObject) {
        try {
            String taskbookId = jsonObject.getString("id");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONArray.fromObject(taskBookSystemInfoMapper.selectSystemByTaskbookId(taskbookId), jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    public boolean deleteByIds(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray ids = jsonObject.getJSONArray("ids");
            for (int i = 0; i < ids.size(); i++) {
                taskBookSystemInfoMapper.deleteByTaskBookId((String) ids.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    public boolean deleteByIds(String id) {
        try {
            taskBookSystemInfoMapper.deleteByTaskBookId(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

}
