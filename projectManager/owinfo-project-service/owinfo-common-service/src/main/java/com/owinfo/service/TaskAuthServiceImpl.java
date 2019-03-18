package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.dao.TaskAuthMapper;
import com.owinfo.entity.TaskAuth;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/29.
 */
@RestController
@RequestMapping("/taskAuth")
public class TaskAuthServiceImpl extends AbstractService<TaskAuth>{

    private static final Logger logger = LoggerFactory.getLogger(TaskAuthServiceImpl.class);

    @Autowired
    private TaskAuthMapper taskAuthMapper;

    @RequestMapping(value = "/getByNode",method = RequestMethod.POST)
    public JSONObject getByNode(@RequestBody JSONObject jsonObject){
        try{
            String node=jsonObject.getString("node");
            return JSONObject.fromObject(taskAuthMapper.getByNode(node));
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}
