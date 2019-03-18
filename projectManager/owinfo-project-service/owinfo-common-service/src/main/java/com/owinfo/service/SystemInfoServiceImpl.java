package com.owinfo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.owinfo.core.AbstractService;
import com.owinfo.dao.SystemInfoMapper;
import com.owinfo.entity.SystemInfo;
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
 * Created by Administrator on 2018/1/25.
 */
@RestController
@RequestMapping("/systemInfo")
public class SystemInfoServiceImpl extends AbstractService<SystemInfo> {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfoServiceImpl.class);

    @Autowired
    private SystemInfoMapper systemInfoMapper;
    @Autowired
    private CustServiceImpl custService;

    //查询系统名称是否重复
    @RequestMapping(value = "/findSystemBySystemName", method = RequestMethod.POST)
    public boolean findSystemBySystemName(@RequestBody JSONObject jsonObject) {
        try {
            String systemName = "";
            Boolean repeat = false;
            int count = 0;
            if (!"".equals(jsonObject.getString("systemName"))) {
                systemName = jsonObject.getString("systemName");
                count = systemInfoMapper.findSystemBySystemName(systemName);

                if(count>0){
                    repeat = true;
                }
            }
            return repeat;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return true;
        }
    }

    @RequestMapping(value = "/listForAll", method = RequestMethod.POST)
    public JSONObject listForAll(@RequestBody JSONObject jsonObject) {
        try {
            Integer page = 0;
            Integer size = 10;
            String systemName = null;
            if (!"".equals(jsonObject.getString("page"))) {
                page = Integer.parseInt(jsonObject.getString("page"));
            }
            if (!"".equals(jsonObject.getString("size"))) {
                size = Integer.parseInt(jsonObject.getString("size"));
            }
            if (!"".equals(jsonObject.getString("systemName"))&&null!=jsonObject.getString("systemName")) {
                systemName = jsonObject.getString("systemName");
            }
            PageHelper.startPage(page, size);
            List<SystemInfo> systemInfos = systemInfoMapper.listForAll(systemName);
            PageInfo info = new PageInfo(systemInfos);
            return JSONObject.fromObject(info);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public boolean deleteByIds(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray ids = jsonObject.getJSONArray("ids");
            for (int i = 0; i < ids.size(); i++) {
                systemInfoMapper.deleteById1(ids.getString(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "/getByName", method = RequestMethod.POST)
    public JSONObject getByName(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            if (!"".equals(jsonObject.getString("name"))) {
                param = jsonObject.getString("name");
            }
            JSONObject result = new JSONObject();
            result.put("list", systemInfoMapper.getByName(param));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    //下次版本修改
    @RequestMapping(value = "/getTree", method = RequestMethod.POST)
    public JSONArray getTree(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray result = new JSONArray();
            String parent = jsonObject.getString("parent");
            Integer level = jsonObject.getInt("level");
            JSONArray custEntities = custService.getChild(parent, level, false, true);
            result.addAll(custEntities);
            if (!"".equals(parent)) {
                List<SystemInfo> systemInfos = systemInfoMapper.getByDept(parent);
                for (SystemInfo systemInfo : systemInfos) {
                    JSONObject temp = new JSONObject();
                    temp.put("isChoose", true);
                    temp.put("hasChild", false);
                    temp.put("guid", systemInfo.getId());
                    temp.put("name", systemInfo.getSystemName());
                    temp.put("organizerId", systemInfo.getOrganizerId());
                    temp.put("organizerName", systemInfo.getOrganizerName());
                    temp.put("organizerFullpath", systemInfo.getOrganizerFullpath());
                    temp.put("unitId", systemInfo.getManagerId());
                    temp.put("unitName", systemInfo.getManagerName());
                    temp.put("unitFullpath", systemInfo.getManagerFullpath());
                    result.add(temp);
                }
            }
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
            List<SystemInfo> systemInfos = systemInfoMapper.listByName(jsonObject.getString("name"));
            JSONArray result = new JSONArray();
            for (SystemInfo systemInfo : systemInfos) {
                JSONObject temp = new JSONObject();
                temp.put("isChoose", true);
                temp.put("hasChild", false);
                temp.put("guid", systemInfo.getId());
                temp.put("name", systemInfo.getSystemName());
                temp.put("organizerId", systemInfo.getOrganizerId());
                temp.put("organizerName", systemInfo.getOrganizerName());
                temp.put("organizerFullpath", systemInfo.getOrganizerFullpath());
                temp.put("unitId", systemInfo.getManagerId());
                temp.put("unitName", systemInfo.getManagerName());
                temp.put("unitFullpath", systemInfo.getManagerFullpath());
                String fullpath = systemInfo.getDeptFullpath();
                String[] fullpaths=fullpath.split("\\\\");
                if (null != fullpath && !"".equals(fullpath) &&fullpaths.length > 1) {
                    temp.put("parent", fullpath.split("\\\\")[1]);
                } else {
                    temp.put("parent", fullpath);
                }
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
