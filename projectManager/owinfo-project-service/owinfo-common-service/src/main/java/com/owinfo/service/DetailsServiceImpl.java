package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.DetailsMapper;
import com.owinfo.dao.STMSystemInfoMapper;
import com.owinfo.entity.Details;
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
 * Created by Administrator on 2017/12/14.
 */
@RestController
@RequestMapping("/details")
public class DetailsServiceImpl extends AbstractService<Details> {

    private static final Logger logger = LoggerFactory.getLogger(DetailsServiceImpl.class);

    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private STMSystemInfoMapper stmSystemInfoMapper;

    @RequestMapping(value = "findDetailByDetailId", method = RequestMethod.POST)
    public JSONObject findDetailByDetailId(@RequestBody JSONObject jsonObject) {
        String id = "";
        try {
            id = jsonObject.getString("detailId");
            Details details = detailsMapper.findDetailByDetailId(id);
            return JSONObject.fromObject(details);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "findSystemCountBySystemName", method = RequestMethod.POST)
    public JSONObject findSystemCountBySystemName(@RequestBody JSONObject jsonObject) {
        int count = 0;
        String systemName = null;
        String systemId = null;
        JSONObject result = new JSONObject();
        try {
            if (null != jsonObject.getString("systemName") && !"".equals(jsonObject.getString("systemName"))) {
                systemName = jsonObject.getString("systemName");
//                systemName = systemName.substring(0, systemName.length() - 4);
                count = detailsMapper.findSystemCountBySystemName(systemName);
                result.put("count", count);
                systemId = stmSystemInfoMapper.findSystemIdBySystemName(systemName);
                result.put("systemId", systemId);
            }
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
                detailsMapper.deleteById1((String) ids.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "listByTaskBookId", method = RequestMethod.POST)
    public JSONArray listByTaskBookId(@RequestBody JSONObject jsonObject) {
        try {
            String taskbookId = jsonObject.getString("id");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            return JSONArray.fromObject(detailsMapper.listByTaskBookId(taskbookId), jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "deleteByTaskBookId", method = RequestMethod.POST)
    public boolean deleteByTaskBookId(String taskbookId) {
        try {
            detailsMapper.deleteByTaskBookId(taskbookId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "deleteBySystemId", method = RequestMethod.POST)
    public boolean deleteBySystemId(String systemId) {
        try {
            detailsMapper.deleteBySystemId(systemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    public boolean deleteById1(String id) {
        try {
            detailsMapper.deleteById1(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    public boolean hasId(String id) {
        try {
            String finalId = detailsMapper.hasId(id);
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

    public Integer numByStatus(String taskbookId, String status) {
        return detailsMapper.numByStatus(taskbookId, status);
    }

    @RequestMapping(value = "checkNum", method = RequestMethod.POST)
    public boolean checkNum(@RequestBody JSONObject jsonObject) {
        try {
            String s = detailsMapper.checkNum(jsonObject.getString("detailsNum"));
            if (null != s && !"".equals(s)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }


    @RequestMapping(value = "/findDetailsLikeDetailsName", method = RequestMethod.POST)
    public JSONObject findDetailsLikeDetailsName(@RequestBody JSONObject jsonObject) {
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            String param = "";
            if (!"".equals(jsonObject.getString("detailsName"))) {
                param = jsonObject.getString("detailsName");
            }
            JSONObject result = new JSONObject();
            List<Details> detailsList = detailsMapper.findDetailsLikeDetailsName(param);
            JSONArray list=JSONArray.fromObject(detailsList,jsonConfig);
            result.put("list", list);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "/findDetailsLikeVersion", method = RequestMethod.POST)
    public JSONObject findDetailsLikeVersion(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            if (!"".equals(jsonObject.getString("version"))) {
                param = jsonObject.getString("version");
            }
            JSONObject result = new JSONObject();
            result.put("list", detailsMapper.findDetailsLikeVersion(param));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}
