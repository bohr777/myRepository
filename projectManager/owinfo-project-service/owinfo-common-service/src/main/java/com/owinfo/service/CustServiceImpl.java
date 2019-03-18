package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.dao.CustMapper;
import com.owinfo.entity.CustEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
@RestController
@RequestMapping("/cservice")
public class CustServiceImpl extends AbstractService<CustEntity> {

    private static final Logger logger = LoggerFactory.getLogger(CustServiceImpl.class);

    @Autowired
    private CustMapper custMapper;

    @RequestMapping(value = "custByInfo", method = RequestMethod.POST)
    public JSONArray cust(@RequestBody JSONObject jsonObject) {
        try {
            List<CustEntity> listCustEntuty = custMapper.selectByInfo(jsonObject.getString("displayName"));
            return JSONArray.fromObject(listCustEntuty);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    public JSONArray getChild(String parent, Integer level, boolean isChoose, boolean hasChild) {
        try {
            JSONArray result = new JSONArray();
            if (level < 1) {
                level = 1;
            }
            if (level == 1) {
                parent = "";
            }
            List<CustEntity> custEntities = custMapper.getChild(parent, level);
            for (CustEntity custEntity : custEntities) {
                JSONObject temp = JSONObject.fromObject(custEntity);
                temp.put("isChoose", isChoose);
                temp.put("hasChild", hasChild);
                result.add(temp);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    public JSONArray getTree(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray result = getChild(jsonObject.getString("parent"), jsonObject.getInt("level"), true, true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "searchInTree", method = RequestMethod.POST)
    public JSONArray searchInTree(@RequestBody JSONObject jsonObject) {
        try {
            List<CustEntity> custEntities = custMapper.listByName(jsonObject.getString("name"));
            JSONArray result = new JSONArray();
            for (CustEntity custEntity : custEntities) {
                JSONObject temp = JSONObject.fromObject(custEntity);
                String fullpath = custEntity.getAllPathName();
                String[] fullpaths = fullpath.split("\\\\");
                if (null != fullpath && !"".equals(fullpath) && fullpaths.length > 2) {
                    temp.put("parent", fullpaths[1]);
                } else if (fullpaths.length == 2) {
                    temp.put("parent", fullpaths[0]);
                } else {
                    temp.put("parent", fullpath);
                }
                temp.put("isChoose", true);
                temp.put("hasChild", false);
                result.add(temp);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}
