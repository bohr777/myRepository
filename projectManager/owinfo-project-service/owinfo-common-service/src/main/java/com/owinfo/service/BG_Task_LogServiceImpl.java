package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.BG_Task_logMapper;
import com.owinfo.entity.BG_Task_Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Bruce on 2017/12/19.
 */
@RestController
@RequestMapping("/BG_Task_Log")
public class    BG_Task_LogServiceImpl extends AbstractService<BG_Task_Log> {

    private static final Logger logger = LoggerFactory.getLogger(BG_Task_LogServiceImpl.class);


    @Autowired
    private BG_Task_logMapper BG_Task_log;

    @RequestMapping(method = RequestMethod.POST, value = "findRecord")
    public JSONArray findRecord(@RequestBody JSONObject jsonObject) {
        try{
            List<BG_Task_Log> task1 = BG_Task_log.findRecord((String) jsonObject.get("guid"));
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONArray.fromObject(task1, jsonConfig);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findTaskId")
    public JSONArray findTaskId(@RequestBody JSONObject jsonObject) {
        try{
            List<BG_Task_Log> task2 = BG_Task_log.findTaskId((String) jsonObject.get("taskid"));
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONArray.fromObject(task2, jsonConfig);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveLog")
    public boolean saveLog(@RequestBody JSONObject jsonObject) {
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("logDate",sdf.parse(jsonObject.getString("logDate")));
            String uuid = UUID.randomUUID().toString();
            jsonObject.put("guid", uuid);
            jsonObject.put("createTime",new Date());
            return BG_Task_log.insertSave((BG_Task_Log) JSONObject.toBean(jsonObject, BG_Task_Log.class));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "updateByGUID", method = RequestMethod.POST)
    public boolean updateByGUID(@RequestBody JSONObject jsonObject) {
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("logDate",sdf.parse(jsonObject.getString("logDate")));
            return BG_Task_log.updateByGUID((BG_Task_Log) JSONObject.toBean(jsonObject, BG_Task_Log.class));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
}
