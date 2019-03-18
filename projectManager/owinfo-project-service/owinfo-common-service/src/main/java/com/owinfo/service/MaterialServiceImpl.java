package com.owinfo.service;
import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.MaterialMapper;
import com.owinfo.entity.Material;
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
 * Created by Administrator on 2017/12/7.
 */
@RestController
@RequestMapping("/material")
public class MaterialServiceImpl extends AbstractService<Material>{

    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);


    @Autowired
    private MaterialMapper materialMapper;

    @RequestMapping(value = "deleteByIds",method = RequestMethod.POST)
    public boolean deleteById1(@RequestBody JSONObject jsonObject){
        try{
            JSONArray ids=jsonObject.getJSONArray("ids");
            for(int i=0;i<ids.size();i++){
                materialMapper.deleteByPrimaryKey(ids.getString(i));
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
    @RequestMapping(value = "listForAll",method = RequestMethod.POST)
    public JSONObject listForAll(){
        try{
            return JSONObject.fromObject(materialMapper.listForAll());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "getById1",method = RequestMethod.POST)
    public JSONObject getById(@RequestBody JSONObject jsonObject){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        return JSONObject.fromObject(materialMapper.getById(jsonObject.getString("id")),jsonConfig);
    }
    @RequestMapping(value = "isLocal",method = RequestMethod.POST)
    public boolean isLocal(@RequestBody JSONObject jsonObject){
        try{
            String id=jsonObject.getString("id");
            int i=materialMapper.isLocal(id);
            if(i>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
}
