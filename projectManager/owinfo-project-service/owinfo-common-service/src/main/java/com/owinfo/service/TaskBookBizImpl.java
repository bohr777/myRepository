package com.owinfo.service;

import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.DetailsMapper;
import com.owinfo.dao.MaterialMapper;
import com.owinfo.dao.STMSystemInfoMapper;
import com.owinfo.dao.TaskBookSystemInfoMapper;
import com.owinfo.entity.Material;
import com.owinfo.entity.SystemInfoResult;
import com.owinfo.entity.TaskBookSystemInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;



/**
 * Created by Administrator on 2017/12/13.
 */
@RestController
@RequestMapping("taskbook")
public class TaskBookBizImpl {

    private static final Logger logger = LoggerFactory.getLogger(TaskBookBizImpl.class);

    @Autowired
    private TaskBookServiceImpl taskBookService;
    @Autowired
    private SystemsServiceImpl systemsService;
    @Autowired
    private SystemsBizImpl systemsBiz;
    @Autowired
    private DetailsServiceImpl detailsService;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private TaskInfoServiceImpl taskInfoService;
    @Autowired
    private TaskOpinionServiceImpl taskOpinionService;
    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private STMSystemInfoServiceImpl stmSystemInfoService;
    @Autowired
    private TaskBookSystemInfoImpl taskBookSystemInfoService;
    @Autowired
    private TaskBookSystemInfoMapper taskBookSystemInfoMapper;
    @Autowired
    private STMSystemInfoMapper stmSystemInfoMapper;


    @RequestMapping(value = "deskGet", method = RequestMethod.POST)
    public JSONObject deskGet(@RequestBody JSONObject jsonObject) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        try {

//            String taskId = (String)jsonObject.get("taskId");
//            String id = taskInfoService.findResourceIdByActivityId(taskId);

            String id = (String) jsonObject.get("id");

            JSONObject result = new JSONObject();
            JSONObject a = new JSONObject();
            a.put("id", id);
            if (null != taskOpinionService.listByResourceId(a)) {
                result.put("opinions", taskOpinionService.listByResourceId(a));
            }
            JSONObject taskBook = taskBookService.getById(jsonObject);
            if (taskBook.has("tag") && !"".equals(taskBook.getString("tag"))) {
                String tag = taskBook.getString("tag");
                JSONArray tags = JSONArray.fromObject(tag.split(","));
                taskBook.put("tag", tags);
            }
            String c = taskBook.getString("competentDept");
            String[] cs = c.split(",");
            JSONObject f = new JSONObject();
            for (int i = 0; i < cs.length; i++) {
                String[] temp = cs[i].split(":");
                if (temp.length > 1) {
                    f.put(temp[0], temp[1]);
                }
            }
            taskBook.put("competentDept", f);
            taskBook.put("status", getStatus(id, "1"));
            JSONArray taskbookFile = new JSONArray();
            List<Material> materials = materialMapper.listByResourceId(id);
            if (null != materials) {
                for (int i = 0; i < materials.size(); i++) {
                    JSONObject temp = new JSONObject();
                    temp.put("id", materials.get(i).getId());
                    temp.put("name", materials.get(i).getTitle());
                    taskbookFile.add(temp);
                }
                taskBook.put("file", taskbookFile);
                materials = null;
            }
            result.put("taskBook", taskBook);
            JSONArray systems = taskBookSystemInfoService.selectSystemByTaskbookId(jsonObject);
            String isDetailDelete="";
            boolean systemDeleteFlag=false;
            for (int i = systems.size()-1; i >=0; i--) {
                JSONObject system = systems.getJSONObject(i);
                String systemId = (String) system.get("systemId");
                List<SystemInfoResult> systemInfoResultList = stmSystemInfoMapper.selectSystemById(systemId);
                String path = "";
                String systemName = "";
                for (SystemInfoResult systemInfoResult : systemInfoResultList) {
                    path += systemInfoResult.getOgusPath() + "-";
//                    if ("2".equals(systemInfoResult.getSystemTier())) {
//                        systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(二级)");
//                    }
//                    if ("3".equals(systemInfoResult.getSystemTier())) {
//                        systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(三级)");
//                    }
                    systemName = systemInfoResult.getSystemName();
                }
                path=path.substring(0, path.length() - 1);
                if(jsonObject.has("auth")&&("任务拆分负责人".equals(jsonObject.getString("auth"))||"任务拆分".equals(jsonObject.getString("auth")))) {
                    String[] paths=path.split("-");
                    for(String temp:paths){
//                        if(jsonObject.getString("userPath").replaceAll("\\\\","/").indexOf(temp.replaceAll("\\\\","/"))==-1){
                        if(jsonObject.getString("userPath").indexOf(temp)==-1){
                            systemDeleteFlag=true;
                            break;
                        }
                    }
                }
                if(systemDeleteFlag){
                    isDetailDelete+=systemId+",";
                    systems.remove(i);
                    systemDeleteFlag=false;
                }else{
                    system.put("frequency", detailsMapper.frequencyBySystemId(system.getString("systemId")));
                    system.put("path", path);
                    system.put("systemName", systemName);
                }
            }
            result.put("systems", systems);
            JSONArray details = detailsService.listByTaskBookId(jsonObject);
            for (int i = details.size()-1; i >=0 ; i--) {
                JSONObject detail = details.getJSONObject(i);
                if(isDetailDelete.indexOf(detail.getString("systemId"))!=-1){
                    details.remove(i);
                    continue;
                }
                JSONArray detailsFile = new JSONArray();
                materials = materialMapper.listByResourceId(detail.getString("id"));
                if (null != materials) {
                    for (int j = 0; j < materials.size(); j++) {
                        JSONObject temp = new JSONObject();
                        temp.put("id", materials.get(j).getId());
                        temp.put("name", materials.get(j).getTitle());
                        detailsFile.add(temp);
                    }
                    details.getJSONObject(i).put("file", detailsFile);
                    materials = null;
                }
            }
            result.put("details", details);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }


    @RequestMapping(value = "tempSave", method = RequestMethod.POST)
    public boolean tempSave(@RequestBody JSONObject jsonObject) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject taskBook = jsonObject.getJSONObject("taskBook");
            try {
                taskBook.put("founderId", jsonObject.getString("founderId"));
                taskBook.put("founderName", jsonObject.getString("founderName"));
                taskBook.put("founderFullpath", jsonObject.getString("founderFullpath"));
                taskBook.put("finalModifierId", jsonObject.getString("finalModifierId"));
                taskBook.put("finalModifierName", jsonObject.getString("finalModifierName"));
                taskBook.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
            } catch (Exception e) {
                int i = 1;
            }
            taskBook.put("finalModify", new Date());
            taskBook.put("isDelete", "0");
            if (taskBook.has("createTime")) {
                if (!"".equals(taskBook.getString("createTime"))) {
                    taskBook.put("createTime", sdf1.parse(taskBook.getString("createTime")));
                } else {
                    taskBook.remove("createTime");
                }
            }
            if (taskBook.has("competentDept")) {
                JSONObject competentDept = taskBook.getJSONObject("competentDept");
                if (competentDept.size() > 0) {
                    taskBook.put("competentDept", "id:" + competentDept.getString("id") + ",name:" + competentDept.getString("name") + ",fullpath:" + competentDept.getString("fullpath"));
                }
            }
            if (taskBook.has("tag")) {
                JSONArray tag = taskBook.getJSONArray("tag");
                if (tag.size() > 0) {
                    String tags = new String();
                    for (int i = 0; i < tag.size(); i++) {
                        tags += tag.getString(i) + ",";
                    }
                    if (tag.size() > 0) {
                        taskBook.put("tag", tags.substring(0, tags.length() - 1));
                    } else {
                        taskBook.put("tag", "");
                    }
                } else {
                    taskBook.put("tag", "");
                }
            }
            if (taskBook.has("mailTime")) {
                if (!"".equals(taskBook.getString("mailTime"))) {
                    taskBook.put("mailTime", sdf.parse(taskBook.getString("mailTime")));
                } else {
                    taskBook.remove("mailTime");
                }
            }
            if (taskBook.has("issueTime")) {
                if (!"".equals(taskBook.getString("issueTime"))) {
                    taskBook.put("issueTime", sdf.parse(taskBook.getString("issueTime")));
                } else {
                    taskBook.remove("issueTime");
                }
            }
            if (taskBook.has("planTime")) {
                if (!"".equals(taskBook.getString("planTime"))) {
                    taskBook.put("planTime", sdf.parse(taskBook.getString("planTime")));
                } else {
                    taskBook.remove("planTime");
                }
            }
            if (taskBook.has("finishTime")) {
                if (!"".equals(taskBook.getString("finishTime"))) {
                    taskBook.put("finishTime", sdf.parse(taskBook.getString("finishTime")));
                } else {
                    taskBook.remove("finishTime");
                }
            }
            boolean shifou = taskBookService.save(taskBook);
//            logger.info("!!!!!临时添加taskbook!!!!!");
//            logger.info("添加taskbook结果     " + shifou);
//            logger.info("*************"+taskBook.toString());
//            logger.info("!!!!!临时添加taskbook!!!!!");

            if (jsonObject.has("systems")) {
                JSONArray taskBookSystemInfos = jsonObject.getJSONArray("systems");
                if (taskBookSystemInfos.size() > 0) {
                    for (int i = 0; i < taskBookSystemInfos.size(); i++) {
                        String id = UUID.randomUUID().toString();
                        JSONObject taskBookSystemInfo = taskBookSystemInfos.getJSONObject(i);
                        taskBookSystemInfo.put("id", id);
                        taskBookSystemInfo.put("taskbookId", taskBookSystemInfo.getString("taskbookId"));
                        taskBookSystemInfo.put("systemId", taskBookSystemInfo.getString("systemId"));
                        taskBookSystemInfo.put("isDelete", "0");
                        taskBookSystemInfo.remove("systemName");
                        int row = taskBookSystemInfoMapper.insertSelective((TaskBookSystemInfo) JSONObject.toBean(taskBookSystemInfo, TaskBookSystemInfo.class));

//                        logger.info("!!!!!!临时添加taskBookSystemInfo!!!!!!!");
//                        logger.info("添加taskBookSystemInfo结果     " + row);
//                        logger.info("*************"+((TaskBookSystemInfo) JSONObject.toBean(taskBookSystemInfo, TaskBookSystemInfo.class)).toString());
//                        logger.info("!!!!!!临时添加taskBookSystemInfo!!!!!!!");

                    }
                }
            }
            if (jsonObject.has("details")) {
                JSONArray details = jsonObject.getJSONArray("details");
                if (details.size() > 0) {
                    for (int i = 0; i < details.size(); i++) {
                        JSONObject detail = details.getJSONObject(i);
                        detail.put("createTime", new Date());
                        detail.put("finalModify", new Date());
                        detail.put("isDelete", "0");
                        try {
                            detail.put("founderId", jsonObject.getString("founderId"));
                            detail.put("founderName", jsonObject.getString("founderName"));
                            detail.put("founderFullpath", jsonObject.getString("founderFullpath"));
                            detail.put("finalModifierId", jsonObject.getString("finalModifierId"));
                            detail.put("finalModifierName", jsonObject.getString("finalModifierName"));
                            detail.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
                        } catch (Exception e) {
                            int mm = 1;
                        }
                        if (detail.has("planTime")) {
                            if (!"".equals(detail.getString("planTime"))) {
                                detail.put("planTime", sdf.parse(detail.getString("planTime")));
                            } else {
                                detail.remove("planTime");
                            }
                        }
                        if (detail.has("finishTime")) {
                            if (!"".equals(detail.getString("finishTime"))) {
                                detail.put("finishTime", sdf.parse(detail.getString("finishTime")));
                            } else {
                                detail.remove("finishTime");
                            }
                        }
                        detail.remove("file");
                        String systemName = detail.get("systemName").toString();
//                        detail.put("systemName", systemName.substring(0, systemName.length() - 4));
                        boolean detailsResult = detailsService.save(detail);

//                        logger.info("!!!!!!!!临时添加detail!!!!!!!!");
//                        logger.info("添加detail结果     " + detailsResult);
//                        logger.info("*************"+detail.toString());
//                        logger.info("!!!!!!!!临时添加detail!!!!!!!!");

                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "whollySave", method = RequestMethod.POST)
    @Transactional
    public boolean whollySave(@RequestBody JSONObject jsonObject) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject taskBook = jsonObject.getJSONObject("taskBook");
            try {
                taskBook.put("founderId", jsonObject.getString("founderId"));
                taskBook.put("founderName", jsonObject.getString("founderName"));
                taskBook.put("founderFullpath", jsonObject.getString("founderFullpath"));
                taskBook.put("finalModifierId", jsonObject.getString("finalModifierId"));
                taskBook.put("finalModifierName", jsonObject.getString("finalModifierName"));
                taskBook.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
            } catch (Exception e) {
                int i = 1;
            }
            taskBook.put("finalModify", new Date());
            taskBook.put("isDelete", "0");
            if (taskBook.has("createTime")) {
                if (!"".equals(taskBook.getString("createTime"))) {
                    taskBook.put("createTime", sdf1.parse(taskBook.getString("createTime")));
                } else {
                    taskBook.remove("createTime");
                }
            }
            if (taskBook.has("competentDept")) {
                JSONObject competentDept = taskBook.getJSONObject("competentDept");
                taskBook.put("competentDept", "id:" + competentDept.getString("id") + ",name:" + competentDept.getString("name") + ",fullpath:" + competentDept.getString("fullpath"));
            }
            if (taskBook.has("tag")) {
                JSONArray tag = taskBook.getJSONArray("tag");
                String tags = new String();
                for (int i = 0; i < tag.size(); i++) {
                    tags += tag.getString(i) + ",";
                }
                if (tag.size() > 0) {
                    taskBook.put("tag", tags.substring(0, tags.length() - 1));
                } else {
                    taskBook.put("tag", "");
                }
            }
            if (taskBook.has("mailTime")) {
                if (!"".equals(taskBook.getString("mailTime"))) {
                    taskBook.put("mailTime", sdf.parse(taskBook.getString("mailTime")));
                } else {
                    taskBook.remove("mailTime");
                }
            }
            if (taskBook.has("issueTime")) {
                if (!"".equals(taskBook.getString("issueTime"))) {
                    taskBook.put("issueTime", sdf.parse(taskBook.getString("issueTime")));
                } else {
                    taskBook.remove("issueTime");
                }
            }
            if (taskBook.has("planTime")) {
                if (!"".equals(taskBook.getString("planTime"))) {
                    taskBook.put("planTime", sdf.parse(taskBook.getString("planTime")));
                } else {
                    taskBook.remove("planTime");
                }
            }
            if (taskBook.has("finishTime")) {
                if (!"".equals(taskBook.getString("finishTime"))) {
                    taskBook.put("finishTime", sdf.parse(taskBook.getString("finishTime")));
                } else {
                    taskBook.remove("finishTime");
                }
            }
            boolean taskBookResult = taskBookService.save(taskBook);

//            logger.info("!!!!!!!!添加taskbook!!!!!!!!!");
//            logger.info("taskbook结果     " + taskBookResult);
//            logger.info("*************"+taskBook.toString());
//            logger.info("!!!!!!!!添加taskbook!!!!!!!!!");

            if (jsonObject.has("systems")) {
                JSONArray taskBookSystemInfos = jsonObject.getJSONArray("systems");
                for (int i = 0; i < taskBookSystemInfos.size(); i++) {
                    String id = UUID.randomUUID().toString();
                    JSONObject taskBookSystemInfo = taskBookSystemInfos.getJSONObject(i);
                    taskBookSystemInfo.put("id", id);
                    taskBookSystemInfo.put("taskbookId", taskBookSystemInfo.getString("taskbookId"));
                    taskBookSystemInfo.put("systemId", taskBookSystemInfo.getString("systemId"));
                    taskBookSystemInfo.put("isDelete", "0");
                    taskBookSystemInfo.remove("systemName");
                    int row = taskBookSystemInfoMapper.insertSelective((TaskBookSystemInfo) JSONObject.toBean(taskBookSystemInfo, TaskBookSystemInfo.class));

//                    logger.info("!!!!!!!!!!添加taskBookSystemInfo!!!!!!!!!!!");
//                    logger.info("taskBookSystemInfo结果     " + row);
//                    logger.info("*************"+((TaskBookSystemInfo) JSONObject.toBean(taskBookSystemInfo, TaskBookSystemInfo.class)).toString());
//                    logger.info("!!!!!!!!!!添加taskBookSystemInfo!!!!!!!!!!!");

                }
            }
            if (jsonObject.has("details")) {
                JSONArray details = jsonObject.getJSONArray("details");
                for (int i = 0; i < details.size(); i++) {
                    JSONObject detail = details.getJSONObject(i);
                    detail.put("createTime", new Date());
                    detail.put("finalModify", new Date());
                    detail.put("isDelete", "0");
                    try {
                        detail.put("founderId", jsonObject.getString("founderId"));
                        detail.put("founderName", jsonObject.getString("founderName"));
                        detail.put("founderFullpath", jsonObject.getString("founderFullpath"));
                        detail.put("finalModifierId", jsonObject.getString("finalModifierId"));
                        detail.put("finalModifierName", jsonObject.getString("finalModifierName"));
                        detail.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
                    } catch (Exception e) {
                        int mm = 1;
                    }
                    if (detail.has("planTime")) {
                        if (!"".equals(detail.getString("planTime"))) {
                            detail.put("planTime", sdf.parse(detail.getString("planTime")));
                        } else {
                            detail.remove("planTime");
                        }
                    }
                    if (detail.has("finishTime")) {
                        if (!"".equals(detail.getString("finishTime"))) {
                            detail.put("finishTime", sdf.parse(detail.getString("finishTime")));
                        } else {
                            detail.remove("finishTime");
                        }
                    }
                    detail.remove("file");
                    String systemName = detail.get("systemName").toString();
//                    detail.put("systemName", systemName.substring(0, systemName.length() - 4));
                    boolean detailsResult = detailsService.save(detail);

//                    logger.info("!!!!!!!!!添加detail!!!!!!!!");
//                    logger.info("添加detail结果     " + detailsResult);
//                    logger.info("*************"+detail.toString());
//                    logger.info("!!!!!!!!!添加detail!!!!!!!!");

                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @Transactional
    @RequestMapping(value = "whollyUpdate", method = RequestMethod.POST)
    public boolean whollyUpdate(@RequestBody JSONObject jsonObject) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject taskBook = jsonObject.getJSONObject("taskBook");
            taskBook.put("finalModify", new Date());
            if (taskBook.has("competentDept")) {
                JSONObject competentDept = taskBook.getJSONObject("competentDept");
                if (competentDept.size() > 0) {
                    taskBook.put("competentDept", "id:" + competentDept.getString("id") + ",name:" + competentDept.getString("name") + ",fullpath:" + competentDept.getString("fullpath"));
                }
            }
            JSONArray tag = taskBook.getJSONArray("tag");
            String tags = new String();
            taskBook.remove("createTime");
            try {
                taskBook.put("finalModifierId", jsonObject.getString("finalModifierId"));
                taskBook.put("finalModifierName", jsonObject.getString("finalModifierName"));
                taskBook.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
            } catch (Exception e) {
                int nn = 1;
            }
            for (int i = 0; i < tag.size(); i++) {
                tags += tag.getString(i) + ",";
            }
            if (tag.size() > 0) {
                taskBook.put("tag", tags.substring(0, tags.length() - 1));
                if (tag.size() == 1 && "".equals(tag.getString(0))) {
                    taskBook.put("tag", "");
                }
            } else {
                taskBook.put("tag", "");
            }
            taskBook.remove("file");
            if (taskBook.has("mailTime")) {
                if (!"".equals(taskBook.getString("mailTime"))) {
                    taskBook.put("mailTime", sdf.parse(taskBook.getString("mailTime")));
                } else {
                    taskBook.remove("mailTime");
                }
            }
            if (taskBook.has("issueTime")) {
                if (!"".equals(taskBook.getString("issueTime"))) {
                    taskBook.put("issueTime", sdf.parse(taskBook.getString("issueTime")));
                } else {
                    taskBook.remove("issueTime");
                }
            }
            if (taskBook.has("planTime")) {
                if (!"".equals(taskBook.getString("planTime"))) {
                    taskBook.put("planTime", sdf.parse(taskBook.getString("planTime")));
                } else {
                    taskBook.remove("planTime");
                }
            }
            if (taskBook.has("finishTime")) {
                if (!"".equals(taskBook.getString("finishTime"))) {
                    taskBook.put("finishTime", sdf.parse(taskBook.getString("finishTime")));
                } else {
                    taskBook.remove("finishTime");
                }
            }
            boolean taskBookResult = taskBookService.update(taskBook);

//            logger.info("!!!!!!!!!!修改taskbook!!!!!!!!!!!!");
//            logger.info("taskbook结果     " + taskBookResult);
//            logger.info("*************"+taskBook.toString());
//            logger.info("!!!!!!!!!!修改taskbook!!!!!!!!!!!!");

            if (jsonObject.has("systems")) {
                JSONArray systems = jsonObject.getJSONArray("systems");
                for (int i = 0; i < systems.size(); i++) {
                    JSONObject taskBookSystemInfos = systems.getJSONObject(i);
                    String id = "";
                    if (taskBookSystemInfos.has("id")) {
                        id = taskBookSystemInfos.getString("id");
                    } else {
                        id = UUID.randomUUID().toString();
                    }
                    String taskbookId = taskBookSystemInfos.getString("taskbookId");
                    String systemId = taskBookSystemInfos.getString("systemId");
                    String isDelete = taskBookSystemInfos.getString("isDelete");
                    TaskBookSystemInfo taskBookSystemInfo = new TaskBookSystemInfo();
                    taskBookSystemInfo.setId(id);
                    taskBookSystemInfo.setIsDelete(isDelete);
                    taskBookSystemInfo.setSystemId(systemId);
                    taskBookSystemInfo.setTaskbookId(taskbookId);
                    Integer count = taskBookSystemInfoMapper.selectCountById(taskbookId, systemId);
                    if (count > 0) {
                        Integer row = taskBookSystemInfoMapper.updateByPrimaryKey(taskBookSystemInfo);

//                        logger.info("!!!!!!!!!!!!不添加新系统  修改taskBookSystemInfo!!!!!!!!!!!");
//                        logger.info("taskBookSystemInfo结果     " + row);
//                        logger.info("*************"+taskBookSystemInfo.toString());
//                        logger.info("!!!!!!!!!!!!不添加新系统  修改taskBookSystemInfo!!!!!!!!!!!");

                    } else {
                        taskBookSystemInfo.setId(UUID.randomUUID().toString());
                        Integer row = taskBookSystemInfoMapper.insert(taskBookSystemInfo);

//                        logger.info("!!!!!!!!!!!!添加新系统  修改taskBookSystemInfo!!!!!!!!!!!");
//                        logger.info("taskBookSystemInfo结果     " + row);
//                        logger.info("*************"+taskBookSystemInfo.toString());
//                        logger.info("!!!!!!!!!!!!添加新系统  修改taskBookSystemInfo!!!!!!!!!!!");

                    }
                }
            }
            if (jsonObject.has("details")) {
                JSONArray details = jsonObject.getJSONArray("details");
                for (int i = 0; i < details.size(); i++) {
                    JSONObject detail = details.getJSONObject(i);
                    detail.remove("file");
                    try {
                        detail.put("finalModifierId", jsonObject.getString("finalModifierId"));
                        detail.put("finalModifierName", jsonObject.getString("finalModifierName"));
                        detail.put("finalModifierFullpath", jsonObject.getString("finalModifierFullpath"));
                    } catch (Exception e) {
                        int cc = 1;
                    }
                    if (detailsService.hasId(detail.getString("id"))) {
                        if (detail.has("planTime")) {
                            if (!"".equals(detail.getString("planTime"))) {
                                detail.put("planTime", sdf.parse(detail.getString("planTime")));
                            } else {
                                detail.remove("planTime");
                            }
                        }
                        if (detail.has("finishTime")) {
                            if (!"".equals(detail.getString("finishTime"))) {
                                detail.put("finishTime", sdf.parse(detail.getString("finishTime")));
                            } else {
                                detail.remove("finishTime");
                            }
                        }
                        detail.put("finalModify", new Date());
                        boolean detailsResult = detailsService.update(detail);

//                        logger.info("!!!!!!!!!修改detail!!!!!!!!");
//                        logger.info("修改detail结果     " + detailsResult);
//                        logger.info("*************"+detail.toString());
//                        logger.info("!!!!!!!!!修改detail!!!!!!!!");

                    } else {
                        if (detail.has("planTime")) {
                            if (!"".equals(detail.getString("planTime"))) {
                                detail.put("planTime", sdf.parse(detail.getString("planTime")));
                            } else {
                                detail.remove("planTime");
                            }
                        }
                        if (detail.has("finishTime")) {
                            if (!"".equals(detail.getString("finishTime"))) {
                                detail.put("finishTime", sdf.parse(detail.getString("finishTime")));
                            } else {
                                detail.remove("finishTime");
                            }
                        }
                        detail.put("founderId", jsonObject.getString("finalModifierId"));
                        detail.put("founderName", jsonObject.getString("finalModifierName"));
                        detail.put("founderFullpath", jsonObject.getString("finalModifierFullpath"));
                        detail.put("createTime", new Date());
                        detail.put("finalModify", new Date());
                        detail.put("isDelete", "0");
                        String systemName = detail.get("systemName").toString();
//                        detail.put("systemName", systemName.substring(0, systemName.length() - 4));
                        boolean detailsResult = detailsService.save(detail);

//                        logger.info("!!!!!!!!!!添加detail!!!!!!!!!");
//                        logger.info("detail结果     " + detailsResult);
//                        logger.info("*************"+detail.toString());
//                        logger.info("!!!!!!!!!!添加detail!!!!!!!!!");

                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "whollyDelete", method = RequestMethod.POST)
    public boolean whollyDelete(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray ids = jsonObject.getJSONArray("ids");
            for (int i = 0; i < ids.size(); i++) {
                detailsMapper.deleteById1(ids.getString(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return false;
        }
    }

    @RequestMapping(value = "whollyGet", method = RequestMethod.POST)
    public JSONObject whollyGet(@RequestBody JSONObject jsonObject) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        try {
            String id = (String) jsonObject.get("id");
            JSONObject result = new JSONObject();
            JSONObject a = new JSONObject();
            a.put("id", id);
            if (null != taskOpinionService.listByResourceId(a)) {
                result.put("opinions", taskOpinionService.listByResourceId(a));
            }
            JSONObject taskBook = taskBookService.getById(jsonObject);
            if (taskBook.has("tag") && !"".equals(taskBook.getString("tag"))) {
                String tag = taskBook.getString("tag");
                JSONArray tags = JSONArray.fromObject(tag.split(","));
                taskBook.put("tag", tags);
            }
            String c = taskBook.getString("competentDept");
            String[] cs = c.split(",");
            JSONObject f = new JSONObject();
            for (int i = 0; i < cs.length; i++) {
                String[] temp = cs[i].split(":");
                if (temp.length > 1) {
                    f.put(temp[0], temp[1]);
                }
            }
            taskBook.put("competentDept", f);
            taskBook.put("status", getStatus(id, "1"));
            JSONArray taskbookFile = new JSONArray();
            List<Material> materials = materialMapper.listByResourceId(id);
            if (null != materials) {
                for (int i = 0; i < materials.size(); i++) {
                    JSONObject temp = new JSONObject();
                    temp.put("id", materials.get(i).getId());
                    temp.put("name", materials.get(i).getTitle());
                    taskbookFile.add(temp);
                }
                taskBook.put("file", taskbookFile);
                materials = null;
            }
            result.put("taskBook", taskBook);
            JSONArray systems = taskBookSystemInfoService.selectSystemByTaskbookId(jsonObject);
            String isDetailDelete="";
            boolean systemDeleteFlag=false;
            for (int i = systems.size()-1; i >=0; i--) {
                JSONObject system = systems.getJSONObject(i);
                String systemId = (String) system.get("systemId");
                List<SystemInfoResult> systemInfoResultList = stmSystemInfoMapper.selectSystemById(systemId);
                String path = "";
                String systemName = "";
                for (SystemInfoResult systemInfoResult : systemInfoResultList) {
                    path += systemInfoResult.getOgusPath() + "-";
//                    if ("2".equals(systemInfoResult.getSystemTier())) {
//                        systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(二级)");
//                    }
//                    if ("3".equals(systemInfoResult.getSystemTier())) {
//                        systemInfoResult.setSystemName(systemInfoResult.getSystemName() + "(三级)");
//                    }
                    systemName = systemInfoResult.getSystemName();
                }
                path=path.substring(0, path.length() - 1);
                if(jsonObject.has("auth")&&("任务拆分负责人".equals(jsonObject.getString("auth"))||"任务拆分".equals(jsonObject.getString("auth")))) {
                    String[] paths=path.split("-");
                    for(String temp:paths){
                        if(jsonObject.getString("userPath").replaceAll("\\\\","/").indexOf(temp.replaceAll("\\\\","/"))==-1){
                            systemDeleteFlag=true;
                            break;
                        }
                    }
                }
                if(systemDeleteFlag){
                    isDetailDelete+=systemId+",";
                    systems.remove(i);
                    systemDeleteFlag=false;
                }else{
                    system.put("frequency", detailsMapper.frequencyBySystemId(system.getString("systemId")));
                    system.put("path", path);
                    system.put("systemName", systemName);
                }
            }
            result.put("systems", systems);
            JSONArray details = detailsService.listByTaskBookId(jsonObject);
            for (int i = details.size()-1; i >=0 ; i--) {
                JSONObject detail = details.getJSONObject(i);
                if(isDetailDelete.indexOf(detail.getString("systemId"))!=-1){
                    details.remove(i);
                    continue;
                }
                JSONArray detailsFile = new JSONArray();
                materials = materialMapper.listByResourceId(detail.getString("id"));
                if (null != materials) {
                    for (int j = 0; j < materials.size(); j++) {
                        JSONObject temp = new JSONObject();
                        temp.put("id", materials.get(j).getId());
                        temp.put("name", materials.get(j).getTitle());
                        detailsFile.add(temp);
                    }
                    details.getJSONObject(i).put("file", detailsFile);
                    materials = null;
                }
            }
            result.put("details", details);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }




    public String getStatus(String id, String status) {
        return detailsService.numByStatus(id, status) + "/" + detailsService.numByStatus(id, "");
    }
}
