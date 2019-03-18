package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.dao.BaseUserMapper;
import com.owinfo.entity.BaseUserEntity;
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
@RequestMapping("/bservice")
public class BaseUserServiceImpl extends AbstractService<BaseUserEntity> {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserServiceImpl.class);

    @Autowired
    private BaseUserMapper baseUserMapper;
    @Autowired
    private CustServiceImpl custService;

    @RequestMapping(value = "selectUserById", method = RequestMethod.POST)
    public JSONObject selectUserById(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            List<BaseUserEntity> users = baseUserMapper.selectUserById(jsonObject.getString("id"));
            for (BaseUserEntity baseUserEntity : users) {
                if (baseUserEntity.getAllPathName().indexOf("总署机关") != -1 || baseUserEntity.getAllPathName().indexOf("海关总署") != -1) {
                    result.put("userId", baseUserEntity.getGuid());
                    result.put("userName", baseUserEntity.getUserName());
                    result.put("userPath", baseUserEntity.getAllPathName());
                    break;
                }else {
                    result.put("userId", baseUserEntity.getGuid());
                    result.put("userName", baseUserEntity.getUserName());
                    result.put("userPath", baseUserEntity.getAllPathName());
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "/selectUserByGUID", method = RequestMethod.POST)
    public JSONObject selectUserByGUID(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            List<BaseUserEntity> users = baseUserMapper.selectUserByGUID(jsonObject.getString("id"));
            BaseUserEntity user = new BaseUserEntity();
            for (BaseUserEntity u : users) {
                user = u;
            }
            result.put("user", user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "/selectUserPathByGUID", method = RequestMethod.POST)
    public JSONObject selectUserPathByGUID(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            List<BaseUserEntity> users = baseUserMapper.selectUserPathByGUID(jsonObject.getString("id"));
            String systemLevels = "";
            result.put("user", users);
            for (BaseUserEntity user : users) {
                if (user.getAllPathName().indexOf("总署机关") != -1 || user.getAllPathName().indexOf("海关总署") != -1) {
//                    result.put("systemLevel", "Administration");
                    systemLevels += "Administration";
                } else {
//                    result.put("systemLevel", "Customhouse");
                    systemLevels += "Customhouse";
                }
            }
            if (systemLevels.indexOf("Administration") != -1 && systemLevels.indexOf("Customhouse") == -1) {
                result.put("systemLevel", "Administration");
            } else if (systemLevels.indexOf("Customhouse") != -1 && systemLevels.indexOf("Administration") == -1) {
                result.put("systemLevel", "Customhouse");
            } else if (systemLevels.indexOf("Administration") != -1 && systemLevels.indexOf("Customhouse") != -1) {
                result.put("systemLevel", "double");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    //模糊查询通过userName，返回10条数据
    @RequestMapping(value = "/baseUserById", method = RequestMethod.POST)
    public JSONArray baseUser(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray result = new JSONArray();
            List<BaseUserEntity> listBaseUser = baseUserMapper.selectUserByInfo((String) jsonObject.get("userName"));
            result.addAll(JSONArray.fromObject(listBaseUser));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }


    @RequestMapping(value = "getTree", method = RequestMethod.POST)
    public JSONArray getTree(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray result = new JSONArray();
            String parent = jsonObject.getString("parent");
            Integer level = jsonObject.getInt("level");
            JSONArray custEntities = custService.getChild(parent, level, false, true);
            result.addAll(custEntities);
            if (!"".equals(parent)) {
                List<BaseUserEntity> users = baseUserMapper.getByDept(parent);
                for (BaseUserEntity user : users) {
                    JSONObject temp = new JSONObject();
                    temp.put("isChoose", true);
                    temp.put("hasChild", false);
                    temp.put("guid", user.getGuid());
                    temp.put("name", user.getUserName());
                    temp.put("allPathName", user.getAllPathName());
                    result.add(temp);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "searchInTree", method = RequestMethod.POST)
    public JSONArray searchInTree(@RequestBody JSONObject jsonObject) {
        try {
            List<BaseUserEntity> userEntities = baseUserMapper.listByName(jsonObject.getString("name"));
            JSONArray result = new JSONArray();
            for (BaseUserEntity userEntity : userEntities) {
                JSONObject temp = new JSONObject();
                String fullpath = userEntity.getAllPathName();
                if (null != fullpath && !"".equals(fullpath)) {
                    temp.put("parent", fullpath.split("\\\\")[1]);
                } else {
                    temp.put("parent", fullpath);
                }
                temp.put("isChoose", true);
                temp.put("hasChild", false);
                temp.put("guid", userEntity.getGuid());
                temp.put("name", userEntity.getUserName());
                temp.put("allPathName", userEntity.getAllPathName());
                result.add(temp);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }
}
