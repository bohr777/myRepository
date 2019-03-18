package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.SetMapper;
import com.owinfo.entity.SetEntity;
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
@RequestMapping("/sservice")
public class SetServiceImpl extends AbstractService<SetEntity> {

    private static final Logger logger = LoggerFactory.getLogger(SetServiceImpl.class);

    @Autowired
    private SetMapper setMapper;

    @RequestMapping(value = "setSave", method = RequestMethod.POST)
    public boolean setSave(@RequestBody JSONObject jsonObject) {
        try{
            SetEntity setEntity = new SetEntity();
            if(jsonObject.has("shu")){
                setEntity.setShu(jsonObject.getJSONArray("shu").toString());
            }
            if(jsonObject.has("guan")){
                setEntity.setGuan(jsonObject.getJSONArray("guan").toString());
            }
            if(jsonObject.has("userId")){
                setEntity.setUserId(jsonObject.getString("userId"));
            }else{
                return false;
            }
//            setEntity.setUnitId(jsonObject.getJSONArray("unitId").toString());
            setEntity.setFounderId(jsonObject.getString("userId"));
            setEntity.setFounderName(jsonObject.getString("founderName"));
            setEntity.setFounderFullpath(jsonObject.getString("founderFullpath"));
            setEntity.setFinalModifierId(jsonObject.getString("finalModifierId"));
            setEntity.setFinalModifierName(jsonObject.getString("finalModifierName"));
            setEntity.setFinalModifierFullpath(jsonObject.getString("finalModifierFullpath"));
            setEntity.setCreateTime(new Date());
            setEntity.setFinalModify(new Date());
            return setMapper.insertSetUp(setEntity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "getSetUpById", method = RequestMethod.POST)
    public JSONObject setUpById(@RequestBody JSONObject jsonObject) {
        try{
            SetEntity setEntity = setMapper.getSetUpById((String) jsonObject.get("userId"));
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONObject.fromObject(setEntity,jsonConfig);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }


}
