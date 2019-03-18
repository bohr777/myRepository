package com.owinfo.service;


import com.owinfo.core.AbstractService;
import com.owinfo.dao.PB_ProjectBaseMapper;
import com.owinfo.dao.SetMapper;
import com.owinfo.entity.PB_ProjectBase;
import com.owinfo.entity.SetEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Bruce on 2017/12/14.
 */
@RestController
@RequestMapping("/PB_ProjectBase")
public class PB_ProjectBaseServiceImpl extends AbstractService<PB_ProjectBase> {

    private static final Logger logger = LoggerFactory.getLogger(PB_ProjectBaseServiceImpl.class);


    @Autowired
    private PB_ProjectBaseMapper pb_ProjectBase;
    @Autowired
    private SetMapper setMapper;
    @Autowired
    private CustServiceImpl custService;

    @RequestMapping(method = RequestMethod.POST, value = "queryProject")
    public JSONArray queryProject(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray items = new JSONArray();
            List<String> follows = new ArrayList();
            String followId = jsonObject.getString("followId");
            if (!"".equals(followId)) {
                SetEntity setEntity = setMapper.getSetUpById(followId);
                if (null != setEntity) {
                    JSONArray temp = JSONArray.fromObject(setEntity.getGuan());
                    temp.addAll(JSONArray.fromObject(setEntity.getShu()));
                    for (int i = 0; i < temp.size(); i++) {
                        follows.add(JSONObject.fromObject(temp.getString(i)).getString("fullPath"));
                    }
                }
            } else {
                follows = null;
            }
            if (follows.size() < 1) {
                follows = null;
            }
            String systemLevel = "";
            if (!"".equals(jsonObject.getString("systemLevel"))) {
                systemLevel = jsonObject.getString("systemLevel");
            }
            List<PB_ProjectBase> pros = pb_ProjectBase.queryProject(jsonObject.getString("subject"), follows,systemLevel);
            for (PB_ProjectBase pb : pros) {
                JSONObject item = new JSONObject();
                item.put("subject", pb.getSubject());
                item.put("organizer_guid", pb.getOrganizer_guid());
                item.put("organizer_name", pb.getOrganizer_name());
                try {
                    if ("".equals(pb.getOrganizer_fullpath()) || null != pb.getOrganizer_fullpath()) {
                        item.put("organizer_fullpath", pb.getOrganizer_fullpath());
                    } else {
                        item.put("organizer_fullpath", "");
                    }
                } catch (Exception e) {
                    item.put("organizer_fullpath", "");
                }
                JSONArray jsonArray = JSONArray.fromObject(pb.getContract_peperson());
                if (jsonArray.isEmpty() == false) {
                    JSONObject p = jsonArray.getJSONObject(0);
                    item.put("person_name", p.get("PersonName"));
                    item.put("person_id", p.get("PersonID"));
                    item.put("person_fullpath", p.get("PersonFullPath"));
                } else {
                    item.put("person_name", "");
                    item.put("person_id", "");
                    item.put("person_fullpath", "");
                }
                items.add(item);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "getTree")
    public JSONArray getTree(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray items = new JSONArray();
            List<String> follows = new ArrayList();
            List<String> shu = new ArrayList();
            List<String> guan = new ArrayList();
            String parent = jsonObject.getString("parent");
            Integer level = jsonObject.getInt("level");
//            if(!"".equals(parent)){
            String followId = jsonObject.getString("followId");
            if (!"".equals(followId)) {
                SetEntity setEntity = setMapper.getSetUpById(followId);
                if (null != setEntity) {
                    JSONArray jGuan = JSONArray.fromObject(setEntity.getGuan());
                    JSONArray jShu = JSONArray.fromObject(setEntity.getShu());
                    for (int i = 0; i < jGuan.size(); i++) {
                        guan.add(JSONObject.fromObject(jGuan.getString(i)).getString("fullPath"));
                    }
                    for (int i = 0; i < jShu.size(); i++) {
                        shu.add(JSONObject.fromObject(jShu.getString(i)).getString("fullPath"));
                    }
                    jGuan.addAll(jShu);
                    for (int i = 0; i < jGuan.size(); i++) {
                        follows.add(JSONObject.fromObject(jGuan.getString(i)).getString("fullPath"));
                    }
                }
            } else {
                follows = null;
            }
            if (follows.size() < 1) {
                follows = null;
            }
            List<PB_ProjectBase> pros = new ArrayList<>();
            if (null != follows && level == 1) {
                JSONObject temp = new JSONObject();
                for (String str : follows) {
                    String[] paths = str.split("\\\\");
                    if (guan.contains(str)) {
                        temp.put("name", paths[paths.length - 1] + "(关级)");
                        temp.put("allPathName", str + "(关级)");
                        guan.remove(str);
                    } else {
                        temp.put("name", paths[paths.length - 1] + "(署级)");
                        temp.put("allPathName", str + "(署级)");
                    }
                    temp.put("levels", paths.length);
                    temp.put("hasChild", true);
                    temp.put("isChoose", false);
                    temp.put("displayName", paths[paths.length - 1]);
                    items.add(temp);
                }
            } else if (null != follows && level > 1) {
                String[] params = parent.split("\\(");
                parent = params[0];
                String type = params[1];
                if (type.startsWith("关级")) {
                    pros = pb_ProjectBase.getByDeptGuan(follows, parent);
                } else {
                    pros = pb_ProjectBase.getByDeptShu(follows, parent);
                }
            } else {
                JSONArray custEntities = custService.getChild(parent, level, false, true);
                items.addAll(custEntities);
                pros = pb_ProjectBase.getByDept(follows, parent);
            }
            if (null != pros && pros.size() > 0) {
                for (PB_ProjectBase pb : pros) {
                    JSONObject item = new JSONObject();
                    item.put("name", pb.getSubject());
                    item.put("organizer_guid", pb.getOrganizer_guid());
                    item.put("organizer_name", pb.getOrganizer_name());
                    try {
                        if ("".equals(pb.getOrganizer_fullpath()) || null != pb.getOrganizer_fullpath()) {
                            item.put("organizer_fullpath", pb.getOrganizer_fullpath());
                        } else {
                            item.put("organizer_fullpath", "");
                        }
                    } catch (Exception e) {
                        item.put("organizer_fullpath", "");
                    }
                    JSONArray jsonArray = JSONArray.fromObject(pb.getContract_peperson());
                    if (jsonArray.isEmpty() == false) {
                        JSONObject p = jsonArray.getJSONObject(0);
                        item.put("person_name", p.get("PersonName"));
                        item.put("person_id", p.get("PersonID"));
                        item.put("person_fullpath", p.get("PersonFullPath"));
                    } else {
                        item.put("person_name", "");
                        item.put("person_id", "");
                        item.put("person_fullpath", "");
                    }
                    item.put("isChoose", true);
                    item.put("hasChild", false);
                    items.add(item);
                }
            }
//            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "searchInTree", method = RequestMethod.POST)
    public JSONArray searchInTree(@RequestBody JSONObject jsonObject) {
        try {
            List<String> follows = new ArrayList();
            String followId = jsonObject.getString("followId");
            List<String> shu = new ArrayList();
            List<String> guan = new ArrayList();
            if (!"".equals(followId)) {
                SetEntity setEntity = setMapper.getSetUpById(followId);
                if (null != setEntity) {
                    JSONArray jGuan = JSONArray.fromObject(setEntity.getGuan());
                    JSONArray jShu = JSONArray.fromObject(setEntity.getShu());
                    for (int i = 0; i < jGuan.size(); i++) {
                        guan.add(JSONObject.fromObject(jGuan.getString(i)).getString("fullPath"));
                    }
                    for (int i = 0; i < jShu.size(); i++) {
                        shu.add(JSONObject.fromObject(jShu.getString(i)).getString("fullPath"));
                    }
                    for (int i = 0; i < jGuan.size(); i++) {
                        follows.add(JSONObject.fromObject(jGuan.getString(i)).getString("fullPath"));
                    }
                    for (int i = 0; i < jShu.size(); i++) {
                        follows.add(JSONObject.fromObject(jShu.getString(i)).getString("fullPath"));
                    }
                }
            } else {
                follows = null;
            }
            if (follows.size() < 1) {
                follows = null;
            }
            String name = jsonObject.getString("name");
            if (!"".equals(name)) {
                JSONArray result = new JSONArray();
                if (null == follows) {
                    List<PB_ProjectBase> queryResult = pb_ProjectBase.listByName(name, null, "");
                    for (PB_ProjectBase pb_projectBase : queryResult) {
                        JSONObject temp = JSONObject.fromObject(pb_projectBase);
                        String fullpath = pb_projectBase.getOrganizer_fullpath();
                        String[] fullpaths = fullpath.split("\\\\");
                        if (null != fullpath && !"".equals(fullpath) && fullpaths.length > 1) {
                            temp.put("parent", fullpaths[1]);
                        } else {
                            temp.put("parent", fullpath);
                        }
                        temp.put("isChoose", true);
                        temp.put("hasChild", false);
                        temp.remove("organizer_fullpath");
                        result.add(temp);
                    }
                } else {
                    if (null != guan && guan.size() > 0) {
                        List<PB_ProjectBase> guans = pb_ProjectBase.listByName(name, guan, "Customhouse");
                        for (PB_ProjectBase g : guans) {
                            JSONObject temp = JSONObject.fromObject(g);
                            String fullpath = g.getOrganizer_fullpath();
                            if (null != fullpath && !"".equals(fullpath)) {
                                String[] fullpaths = g.getOrganizer_fullpath().split("\\\\");
                                temp.put("parent", fullpaths[fullpaths.length - 1] + "(关级)");
                            } else {
                                temp.put("parent", fullpath);
                            }
                            temp.put("isChoose", true);
                            temp.put("hasChild", false);
                            result.add(temp);
                        }
                    }
                    if (null != shu && shu.size() > 0) {
                        List<PB_ProjectBase> shus = pb_ProjectBase.listByName(name, shu, "Administration");
                        for (PB_ProjectBase s : shus) {
                            JSONObject temp = JSONObject.fromObject(s);
                            String fullpath = s.getOrganizer_fullpath();
                            if (null != fullpath && !"".equals(fullpath)) {
                                String[] fullpaths = s.getOrganizer_fullpath().split("\\\\");
                                temp.put("parent", fullpaths[fullpaths.length - 1] + "(署级)");
                            } else {
                                temp.put("parent", fullpath);
                            }
                            temp.put("isChoose", true);
                            temp.put("hasChild", false);
                            result.add(temp);
                        }
                    }
                }
                return result;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
}

