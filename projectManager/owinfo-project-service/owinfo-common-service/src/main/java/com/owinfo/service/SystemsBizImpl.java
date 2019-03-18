package com.owinfo.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/14.
 */

@RestController
@RequestMapping("systems")
public class SystemsBizImpl {

    private static final Logger logger = LoggerFactory.getLogger(SystemsBizImpl.class);


    @Autowired
    private SystemsServiceImpl systemsService;
    @Autowired
    private DetailsServiceImpl detailsService;


    @Transactional
    @RequestMapping(value = "whollyDelete",method = RequestMethod.POST)
    public boolean whollyDelete(@RequestBody JSONObject jsonObject){
        try{
            String id=(String) jsonObject.get("id");
            systemsService.deleteById1(id);
            detailsService.deleteBySystemId(id);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
    @Transactional
    public boolean whollySave(JSONObject system,String taskBookId){
        try{
            String systemId=UUID.randomUUID().toString();
            system.put("id",systemId);
            system.put("createTime",new Date());
            system.put("finalModify",new Date());
            system.put("isDelete","0");
            system.put("taskbookId",taskBookId);
            JSONArray details=system.getJSONArray("details");
            system.remove("details");
            systemsService.save(system);
            for(int j=0;j<details.size();j++){
                JSONObject detail=details.getJSONObject(j);
                detail.put("id",UUID.randomUUID().toString());
                detail.put("createTime",new Date());
                detail.put("finalModify",new Date());
                detail.put("taskbookId",taskBookId);
                detail.put("systemId",systemId);
                detail.put("isDelete","0");
                detailsService.save(detail);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
}
