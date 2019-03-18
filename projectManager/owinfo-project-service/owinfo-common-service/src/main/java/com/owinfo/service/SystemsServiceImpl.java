package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.STMOgusMapper;
import com.owinfo.dao.STMSystemInfoMapper;
import com.owinfo.dao.SystemsMapper;
import com.owinfo.entity.SystemInfoResult;
import com.owinfo.entity.Systems;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
@RestController
@RequestMapping("/systems")
public class SystemsServiceImpl extends AbstractService<Systems> {

    private static final Logger logger = LoggerFactory.getLogger(SystemsServiceImpl.class);

    @Autowired
    private SystemsMapper systemsMapper;
    @Autowired
    private STMSystemInfoMapper stmSystemInfoMapper;
    @Autowired
    private STMOgusMapper stmOgusMapper;

    @RequestMapping(value = "/selectSystemBySystemName", method = RequestMethod.POST)
    public JSONObject selectSystemBySystemName(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            String ogusPath = "";
            if (!"".equals(jsonObject.getString("systemName"))) {
                param = jsonObject.getString("systemName");
//                param = param.substring(0, param.length() - 4);
            }
            JSONObject result = new JSONObject();
            List<SystemInfoResult> systemInfoResultList = stmSystemInfoMapper.selectSystemBySystemName(param);
            String organizerPath = "";
            String id = "";
            String systemName = "";

            for (SystemInfoResult systemInfoResult : systemInfoResultList) {

                organizerPath += systemInfoResult.getOgusPath() + "-";
                id = systemInfoResult.getId();
                systemName = systemInfoResult.getSystemName();


            }
            result.put("id", id);
            result.put("systemName", systemName);
            result.put("organizerPath", organizerPath);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "/selectSystemById", method = RequestMethod.POST)
    public JSONObject selectSystemById(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            String ogusPath = "";
            if (!"".equals(jsonObject.getString("id"))) {
                param = jsonObject.getString("id");
            }
            JSONObject result = new JSONObject();
            List<SystemInfoResult> systemInfoResultList = stmSystemInfoMapper.selectSystemById(param);
            String organizerPath = "";
            String id = "";
            String systemName = "";

            for (SystemInfoResult systemInfoResult : systemInfoResultList) {

                organizerPath += systemInfoResult.getOgusPath() + "-";
                id = systemInfoResult.getId();
                systemName = systemInfoResult.getSystemName();
            }
            result.put("id", id);
            result.put("systemName", systemName);
            result.put("organizerPath", organizerPath);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "/selectSystemLikeSystemName", method = RequestMethod.POST)
    public JSONObject selectSystemLikeSystemName(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            String systemLevel = "";
            if (!"".equals(jsonObject.getString("name"))) {
                param = jsonObject.getString("name");
            }
            if (!"".equals(jsonObject.getString("systemLevel"))) {
                systemLevel = jsonObject.getString("systemLevel");
            }
//            param = param.split("-")[0];
            JSONObject result = new JSONObject();
            List<SystemInfoResult> systemInfoResultList = stmSystemInfoMapper.selectSystemLikeSystemName(param,systemLevel);
            for (SystemInfoResult systemInfoResult : systemInfoResultList) {
                systemInfoResult.setId(systemInfoResult.getId() + "," + systemInfoResult.getSystemName());
//                if ("2".equals(systemInfoResult.getSystemTier())) {
//                    systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(二级)");
//                }
//                if ("3".equals(systemInfoResult.getSystemTier())) {
//                    systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(三级)");
//                }
            }
            result.put("list", systemInfoResultList);
            return result;
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
                systemsMapper.deleteById1((String) ids.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "listForAll", method = RequestMethod.POST)
    public JSONObject listForAll() {
        try {
            return JSONObject.fromObject(systemsMapper.listForAll());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "listByTaskBookId", method = RequestMethod.POST)
    public JSONArray listByTaskBookId(@RequestBody JSONObject jsonObject) {
        try {
            String taskbookId = jsonObject.getString("id");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONArray.fromObject(systemsMapper.listByTaskBookId(taskbookId), jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "deleteByTaskBookId", method = RequestMethod.POST)
    public boolean deleteByTaskBookId(String taskbookId) {
        try {
            systemsMapper.deleteByTaskBookId(taskbookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    public boolean deleteById1(String id) {
        try {
            systemsMapper.deleteById1(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    public boolean hasId(String id) {
        try {
            String finalId = systemsMapper.hasId(id);
            if ("".equals(finalId) || null == finalId) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }
}
