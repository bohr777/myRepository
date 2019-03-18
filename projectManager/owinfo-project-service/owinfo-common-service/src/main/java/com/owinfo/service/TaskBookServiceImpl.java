package com.owinfo.service;

import cn.gov.customs.casp.sdk.h4a.AccreditXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.owinfo.actService.ActivitiService;
import com.owinfo.core.AbstractService;
import com.owinfo.core.JsonDateValueProcessor;
import com.owinfo.dao.DetailsMapper;
import com.owinfo.dao.SetMapper;
import com.owinfo.dao.TaskBookMapper;
import com.owinfo.dao.TaskInfoMapper;
import com.owinfo.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * Created by Administrator on 2017/12/7.
 */
@RestController
@RequestMapping("/taskbook")
public class TaskBookServiceImpl extends AbstractService<TaskBook> {

    private static final Logger logger = LoggerFactory.getLogger(TaskBookServiceImpl.class);

    @Autowired
    private TaskBookMapper taskBookMapper;
    @Autowired
    private TaskBookBizImpl taskBookBiz;
    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private SetMapper setMapper;
    @Autowired
    private ActivitiService activitiService;
    @Value("${managerCode}")
    private String managerCode;
    @Value("${systemCode}")
    private String systemCode;
    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @RequestMapping(value = "/selectTaskbookByProjectName", method = RequestMethod.POST)
    public JSONObject selectTaskbookByProjectName(@RequestBody JSONObject jsonObject) {
        try {
            String param = "";
            if (!"".equals(jsonObject.getString("projectName"))) {
                param = jsonObject.getString("projectName");
            }
            JSONObject result = new JSONObject();
            result.put("list", taskBookMapper.selectTaskbookByProjectName(param));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    public boolean deleteByIds(@RequestBody JSONObject jsonObject) {
        try {
            JSONArray ids = jsonObject.getJSONArray("ids");
            for (int i = 0; i < ids.size(); i++) {
                taskBookMapper.deleteById1((String) ids.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
    }

    @Transactional
    @RequestMapping(value = "listForAll", method = RequestMethod.POST)
    public JSONObject listForAll(@RequestBody JSONObject jsonObject) {
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            List<TaskBook> list = null;
            String mailNumber = "";
            String status = "";
            String projectName = "";
            String competentDept = "";
            String tag = "";
            List<String> follows = new ArrayList();
            String followId = jsonObject.getString("followId");
            if (!"".equals(followId)) {
                SetEntity setEntity = setMapper.getSetUpById(followId);
                if (null != setEntity) {
                    JSONArray temp = JSONArray.fromObject(setEntity.getGuan());
                    temp.addAll(JSONArray.fromObject(setEntity.getShu()));
                    for (int i = 0; i < temp.size(); i++) {
                        follows.add(JSONObject.fromObject(temp.getString(i)).getString("id"));
                    }
                }
            }
            if (follows.size() < 1) {
                follows = null;
            }
            if (jsonObject.has("projectName")) {
                projectName = jsonObject.getString("projectName");
            }
            if (jsonObject.has("competentDept")) {
                competentDept = jsonObject.getString("competentDept");
            }
            if (jsonObject.has("tag")) {
                tag = jsonObject.getString("tag");
            }
            if (jsonObject.has("status")) {
                status = jsonObject.getString("status");
            }
            if (jsonObject.has("mailNumber")) {
                mailNumber = jsonObject.getString("mailNumber");
            }
            Integer page = Integer.parseInt((String) jsonObject.get("page"));
            Integer size = Integer.parseInt((String) jsonObject.get("size"));
            PageHelper.startPage(page, size);
            list = taskBookMapper.listForAll(mailNumber, status, competentDept, projectName, tag, follows);
            for (int i = 0; i < list.size(); i++) {
                TaskBook taskBook = list.get(i);
                taskBook.setStatus(taskBookBiz.getStatus(taskBook.getId(), "1"));
                List<Details> details = detailsMapper.listByTaskBookId(taskBook.getId());
                boolean flag = true;
                Date finishTime = taskBook.getFinishTime();
                if (null == taskBook.getFinishTime() || "".equals(taskBook.getFinishTime())) {
                    for (int j = 0; j < details.size(); j++) {
                        Date temp = details.get(j).getFinishTime();
                        if (null == temp || "".equals(temp)) {
                            flag = false;
                            break;
                        } else {
                            if (null == finishTime || temp.after(finishTime)) {
                                finishTime = temp;
                            }
                        }
                    }
                    if (flag) {
                        taskBook.setFinishTime(finishTime);
                    }
                }
                taskBook.setStatus(taskBookBiz.getStatus(taskBook.getId(), "1"));
                taskBookMapper.updateByPrimaryKey(taskBook);
            }
            for (int i = 0; i < list.size(); i++) {
                Date d1 = list.get(i).getFinalModify();
                for (int j = i + 1; j < list.size(); j++) {
                    Date d2 = list.get(j).getFinalModify();
                    if (d1.before(d2)) {
                        TaskBook temp = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temp);
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).getIssueTime().before(list.get(j).getIssueTime())) {
                        TaskBook temp = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temp);
                    }
                }
            }
            PageInfo info = new PageInfo(list);
//            info.setTotal((long)taskBookMapper.getTotal());
            return JSONObject.fromObject(info, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "getById1", method = RequestMethod.POST)
    public JSONObject getById(@RequestBody JSONObject jsonObject) {
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            String id = (String) jsonObject.get("id");
            return JSONObject.fromObject(taskBookMapper.getById(id), jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    public boolean deleteById1(String id) {
        try {
            taskBookMapper.deleteById1(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
    }

    @RequestMapping(value = "checkNum", method = RequestMethod.POST)
    public boolean checkNum(@RequestBody JSONObject jsonObject) {
        String s = taskBookMapper.checkNum(jsonObject.getString("mailNum"));
        if (null != s && !"".equals(s)) {
            return true;
        }
        return false;
    }


    @Transactional
    @RequestMapping(value = "taskBookResult", method = RequestMethod.POST)
    public JSONObject taskBookResult(@RequestBody JSONObject jsonObject) {
        try {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

            String from = null;//开始时间
            String to = null;//结束时间
            String systemName = null;//系统名称
            String detailsName = null;//任务书名称
            String version = null;//版本号
            String competentDept = null;//业务司局
            String unitName = null;//承办单位
            String status = null;//状态
            String projectName = null;//项目名称


            if (jsonObject.has("from") && !jsonObject.getString("from").equals("")) {
                from = jsonObject.getString("from") + " 00:00:00";
            }
            if (jsonObject.has("to") && !jsonObject.getString("to").equals("")) {
                to = jsonObject.getString("to") + " 23:59:59";
            }
            if (jsonObject.has("systemName") && !jsonObject.getString("systemName").equals("")) {
                systemName = jsonObject.getString("systemName");
//                systemName = systemName.substring(0, systemName.length() - 4);
            }
            if (jsonObject.has("detailsName") && !jsonObject.getString("detailsName").equals("")) {
                detailsName = jsonObject.getString("detailsName");
            }
            if (jsonObject.has("version") && !jsonObject.getString("version").equals("")) {
                version = jsonObject.getString("version");
            }
            if (jsonObject.has("competentDept") && !jsonObject.getString("competentDept").equals("")) {
                competentDept = jsonObject.getString("competentDept");
            }
            if (jsonObject.has("unitName") && !jsonObject.getString("unitName").equals("")) {
                unitName = jsonObject.getString("unitName");
            }
            if (jsonObject.has("status") && !jsonObject.getString("status").equals("")) {
                status = jsonObject.getString("status");
            }
            if (jsonObject.has("projectName") && !jsonObject.getString("projectName").equals("")) {
                projectName = jsonObject.getString("projectName");
            }
            Integer page = Integer.parseInt((String) jsonObject.get("page"));
            Integer size = Integer.parseInt((String) jsonObject.get("size"));
            page = size * (page - 1);
            List<TaskBookResult> taskBookResultList = taskBookMapper.taskBookResult(from, to, systemName, detailsName, version, competentDept, unitName, status, projectName, page, size);

            String uuid = "";
            if (jsonObject.has("uuid") && !"".equals(jsonObject.getString("uuid"))) {
                uuid = jsonObject.getString("uuid");
            }

            for (TaskBookResult taskBookResult : taskBookResultList) {
                String temp = taskBookResult.getCompetentDept();
                if (null != temp && !"".equals(temp)) {
                    taskBookResult.setCompetentDept(temp.split(",")[1].split(":")[1]);
                }
                if ("1".equals(taskBookResult.getTdstatus()) || "2".equals(taskBookResult.getTdstatus()) || "3".equals(taskBookResult.getTdstatus())) {
                    taskBookResult.setStatus("已完成");
                }
                if ("0".equals(taskBookResult.getTdstatus()) || "4".equals(taskBookResult.getTdstatus())) {
                    taskBookResult.setStatus("未完成");
                }
//                List<Details> details = detailsMapper.findDetailByTaskbookId(taskBookResult.getId());
//                String detailResult = "";
//                String allPathName = taskBookResult.getPath();
//                detailResult +=  this.getUsersByRole(allPathName,managerCode)+";";;
//                for (Details detail:details) {
//                    detailResult += detail.getFounderId()+";";
//                    detailResult += detail.getFinalModifierId()+";";
//                }
//                if (detailResult.indexOf(uuid)!=-1) {
//                    taskBookResult.setReadonly("0");
//                }else {
//                    taskBookResult.setReadonly("1");
//                }
                TaskInfo taskInfo = taskInfoMapper.getTaskInfoByResourceId(taskBookResult.getId());
                if (null != taskInfo) {
                    String activityId = taskInfo.getActivityId();
                    JSONObject param = new JSONObject();
                    param.put("processInstanceId", activityId);
                    JSONObject result = activitiService.getTaskManagerAssignee(param);
                    String assignees = result.getString("assignees");
                    if (assignees.indexOf(uuid) != -1) {
                        taskBookResult.setReadonly("0");
                    } else {
                        taskBookResult.setReadonly("1");
                    }
                } else {
                    Details detail = detailsMapper.findDetailByDetailId(taskBookResult.getDetailId());
                    if(detail!=null){
                        if(uuid.equals(detail.getFounderId())){
                            taskBookResult.setIsdetailreadonly("0");
                        }else {
                            taskBookResult.setIsdetailreadonly("1");
                        }
                    }else {
                        taskBookResult.setIsdetailreadonly("1");
                    }
                    taskBookResult.setReadonly("0");
                }
            }
            if (taskBookResultList == null || taskBookResultList.size() < 1) {
                return new JSONObject();
            }
            Map map = new HashMap();
            map.put("list", taskBookResultList);
            map.put("total", taskBookMapper.countTaskBookResult(from, to, systemName, detailsName, version, competentDept, unitName, status, projectName));
//            PageInfo info = new PageInfo(taskBookResultList);
            return JSONObject.fromObject(map, jsonConfig);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    public String getUsersByRole(String path, String role) {
        try {
            Element rt = AccreditXmlReaderHelper.getXmlUsersInRoles(
                    path, OrganizationCategory.ORG_ALL_PATH_NAME,
                    systemCode, AccreditCategory.Code,
                    "CCIC_VIEW", AccreditCategory.Code,
                    role, AccreditCategory.Code,
                    DelegationCategories.All, ""
            );
            StringBuffer result = new StringBuffer();
            NodeList ids = rt.getElementsByTagName("USER_GUID");
            for (int i = 0; i < ids.getLength(); i++) {
                if (i == ids.getLength() - 1) {
                    result.append(ids.item(i).getFirstChild().getNodeValue());
                } else {
                    result.append(ids.item(i).getFirstChild().getNodeValue() + ",");
                }
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

}
