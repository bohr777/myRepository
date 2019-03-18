package com.owinfo.core;


import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public boolean save(@RequestBody JSONObject jsonObject) {
        try{
//            System.out.println("    参数   "+jsonObject.toString());
            mapper.insertSelective((T) JSONObject.toBean(jsonObject,modelClass));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }

    }
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public boolean update(@RequestBody JSONObject jsonObject) {
        try{
            if(jsonObject.has("planTime")){
            }
            mapper.updateByPrimaryKeySelective((T) JSONObject.toBean(jsonObject,modelClass));
            return true;
        }catch(Exception e){
            logger.error(e.toString(),e);
            return false;
        }
    }
    @RequestMapping(value="/getById",method = RequestMethod.POST)
    public JSONObject findById(@RequestBody JSONObject jsonObject) {
        try{
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            String id=null;
            if(jsonObject.has("id")){
                id=jsonObject.getString("id");
            }else if(jsonObject.has("userId")){
                id=jsonObject.getString("userId");
            }else if(jsonObject.has("activityId")){
                id=jsonObject.getString("activityId");
            }
            return JSONObject.fromObject(mapper.selectByPrimaryKey(id),jsonConfig);
        }catch(Exception e){
            logger.error(e.toString(),e);
            return null;
        }
    }
    @RequestMapping(value="deleteById",method = RequestMethod.POST)
    public boolean deleteById(@RequestBody JSONObject jsonObject){
        try{
            if(jsonObject.has("userId")){
                mapper.deleteByPrimaryKey((String)jsonObject.get("userId"));
            }else if(jsonObject.has("id")){
                mapper.deleteByPrimaryKey((String)jsonObject.get("id"));
            }
            return true;
        }catch(Exception e){
            logger.error(e.toString(),e);
            return false;
        }
    }
}
