package com.owinfo.web.controller;

import cn.gov.customs.casp.sdk.h4a.AccreditXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.*;
import com.owinfo.web.util.HBUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpSession;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Administrator on 2017/12/26.
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private TaskOpinionService taskOpinionService;
    @Autowired
    private TaskAuthService taskAuthService;
    @Autowired
    private CustService custService;
    @Autowired
    private HBUtil hbUtil;
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private BaseUserService baseUserService;

    @Value("${managerCode}")
    private String managerCode;
    @Value("${systemCode}")
    private String systemCode;

    @RequestMapping(value = "/deskGetAuth", method = RequestMethod.POST)
    public Result deskGetAuth(@RequestBody JSONObject jsonObject) {
        try {
            String node = "";
            if (jsonObject.has("node")) {
                node = jsonObject.getString("node");
            }
            JSONObject param = new JSONObject();
            JSONObject variables = JSONObject.fromObject(activitiService.getVariable(jsonObject));
//            Object temp = jsonObject.get("variables");
//            if (null != temp && !"".equals(temp)) {
//                variables = (JSONObject) temp;
//            }
            param.put("node", node);
            JSONObject auth = taskAuthService.getByNode(param);
            JSONArray type = new JSONArray();
            if ("任务书负责人".equals(node)) {
                if (null != variables && variables.has("taskBookSZExamine") && "true".equals(variables.getString("taskBookSZExamine"))) {
                    JSONArray auths = JSONArray.fromObject(auth.getString("scene"));
                    for (int i = 0; i < auths.size(); i++) {
                        JSONObject t = auths.getJSONObject(i);
                        if (t.getString("id").equals("taskBook")) {
                            t.put("readOnly", true);
                        }
                        if (t.getString("id").equals("system")) {
                            t.put("readOnly", true);
                        }
                    }
                    auth.put("scene", auths);
                }
                if (null == variables || !variables.has("taskBookSZExamine") || "false".equals(variables.getString("taskBookSZExamine"))) {
                    type.add("00");
                } else if (variables.has("dutyStatus") && "report".equals(variables.getString("dutyStatus"))) {
                    type.add("01");
                    type.add("02");
                } else if ("true".equals(variables.getString("taskBookSZExamine"))) {
                    type.add("01");
                } else {
                    type.add("00");
                    type.add("01");
                    type.add("02");
                }
                auth.put("type", type);
            }
            return ResultGenerator.genSuccessResult(auth);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "deskExecute", method = RequestMethod.POST)
    public Result deskExecute(@RequestBody JSONObject jsonObject) {
        try {
            ThrInOneEntity entity = new ThrInOneEntity();
            if (jsonObject.getString("username") != null && !"".equals(jsonObject.getString("username"))) {
                entity.setUuid(jsonObject.getString("username"));
            }
            String uuid = entity.getUuid();
            JSONObject user = baseUserService.selectUserById(JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}"));
            entity.setUserName(user.getString("userName"));
            entity.setAllPathName(user.getString("userPath"));

            String fromName = "";
            String fromId = "";
            String fromPath = "";

            String executeDetails = "";
            String toNext = "";
            String x = "";
            String t = "";
            JSONArray tos = new JSONArray();
            List<JSONObject> userList = new ArrayList();
            if (null != entity) {
                fromName = entity.getUserName();
                fromId = entity.getUuid();
                fromPath = entity.getAllPathName();
            }
            String type = jsonObject.getString("type");
            JSONObject p = new JSONObject();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("taskId", jsonObject.getString("taskId"));
            JSONObject taskInfo = new JSONObject();
            JSONObject taskOpinion = new JSONObject();
            taskOpinion.put("id", UUID.randomUUID().toString());
//            if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                taskOpinion.put("content", "同意");
//            } else {
            taskOpinion.put("content", jsonObject.getString("content"));
//            }
            taskOpinion.put("opinionTime", new Date());
            taskOpinion.put("activityId", jsonObject.getString("taskId"));
            taskOpinion.put("resourceId", jsonObject.getString("taskbookId"));
            if (null != entity) {
                taskOpinion.put("opinionerId", entity.getUuid());
                taskOpinion.put("opinionerName", entity.getUserName());
                taskOpinion.put("opinionerFullpath", entity.getAllPathName());
            }
            taskInfo.put("resourceId", jsonObject.getString("taskbookId"));
            String title = "";
            JSONObject runInsert = new JSONObject();
            JSONObject runDelete = new JSONObject();
            if (jsonObject.getString("title").indexOf(":") > 0) {
                title = jsonObject.getString("title").replace("-", "").replace(":", "").replace(" ", "");
                taskInfo.put("title", "任务书" + title);
                runInsert.put("title", "任务书" + title);
                runDelete.put("title", "任务书" + title);
            } else {
                title = jsonObject.getString("title");
                taskInfo.put("title", "《" + title + "》任务书");
                runInsert.put("title", "《" + title + "》任务书");
                runDelete.put("title", "《" + title + "》任务书");
            }
            runDelete.put("taskId", jsonObject.getString("taskId"));
            switch (type) {
                case "90":
                    t = jsonObject.getString("assignee");
                    param.put("assignee", t);
                    taskInfo.put("purpose", fromName + "的任务未完成");
                    runInsert.put("purpose", fromName + "的任务未完成");
                    runDelete.put("purpose", fromName + "的任务未完成");
                    x = "{\"id\":" + "\"" + t + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = entity.getUserName();
                    taskOpinion.put("executeDetails", executeDetails);
                    break;
                case "00":
                    param.put("taskStatus", "examine");
                    t = jsonObject.getJSONArray("assignee").getString(0);
                    param.put("assignee", t);
                    taskInfo.put("purpose", fromName + "的任务待审批");
                    runInsert.put("purpose", fromName + "的任务待审批");
                    runDelete.put("purpose", fromName + "的任务待审批");
                    runInsert.put("node", "处长审批(任务书)");

                    x = "{\"id\":" + "\"" + t + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "01":
                    param.put("taskStatus", "handout");
                    JSONArray ja = jsonObject.getJSONArray("assignee");
                    JSONArray PersonliableList = new JSONArray();
                    for (int i = 0; i < ja.size(); i++) {
                        String[] paths = ja.getString(i).split("-");
                        String assignees = "";
                        for (String temp : paths) {
                            assignees += this.getUsersByRole(temp, managerCode) + ",";
                        }
                        PersonliableList.add(assignees.substring(0, assignees.length() - 1));
                    }

                    String personId = "";
                    String xxx = "";
                    for (int i = 0; i < PersonliableList.size(); i++) {
                        if (!"".equals(PersonliableList.getString(i))) {
                            xxx = PersonliableList.getString(i).toCharArray()[0] + "";
                            if (",".equals(xxx)) {
                                personId = PersonliableList.getString(i).substring(1, PersonliableList.getString(i).length());
                            } else {
                                personId = PersonliableList.getString(i);
                            }
                            String[] ids = personId.split(",");
                            for (int index = 0; index <= ids.length - 1; index++) {
                                x = "{\"id\":" + "\"" + ids[index] + "\"}";
                                userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                            }
//                            x = "{\"id\":" + "\"" + personId.split(",")[0] + "\"}";
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                        }

                    }
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext += userJSONObject.getJSONObject("user").getString("userName") + "、";
                    }
                    if (!"".equals(toNext)) {
                        toNext = toNext.substring(0, toNext.length() - 1);
                    }
//                    if (userList.size() > 2) {
//                        StringBuilder sb = new StringBuilder(toNext);
//                        sb.replace(toNext.lastIndexOf("、"), toNext.lastIndexOf("、") + 1, "和");
//                        toNext = sb.toString();
//                    } else if (userList.size() > 0 && userList.size() <= 2) {
//                        toNext = toNext.replace("、", "和");
//                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    param.put("PersonliableList", PersonliableList);
                    taskInfo.put("purpose", fromName + "的任务已下发");
                    runInsert.put("purpose", fromName + "的任务已下发");
                    runDelete.put("purpose", fromName + "的任务已下发");
                    runInsert.put("node", "任务拆分负责人");
                    break;
                case "02":
                    param.put("taskStatus", "end");
                    taskInfo.put("purpose", fromName + "的任务已完成");
                    runInsert.put("purpose", fromName + "的任务已完成");
                    runDelete.put("purpose", fromName + "的任务已完成");
                    taskOpinion.put("executeDetails", "");
                    break;
                case "10":
                    param.put("dutyStatus", "split");
                    param.put("SplitPersonList", jsonObject.getJSONArray("assignee"));
                    taskInfo.put("purpose", fromName + "的任务细分已下发");
                    runInsert.put("purpose", fromName + "的任务细分已下发");
                    runDelete.put("purpose", fromName + "的任务细分已下发");
                    runInsert.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runDelete.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runInsert.put("node", "任务拆分");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "11":
                    param.put("dutyStatus", "examine");
                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "的任务细分待审批");
                    runInsert.put("purpose", fromName + "的任务细分待审批");
                    runDelete.put("purpose", fromName + "的任务细分待审批");
                    runInsert.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runDelete.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runInsert.put("node", "处长审批(任务拆分)");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "12":
                    param.put("dutyStatus", "report");
                    param.put("to", "任务书负责人");
                    taskInfo.put("purpose", fromName + "的任务细分已上报");
                    runInsert.put("purpose", fromName + "的任务细分已上报");
                    runDelete.put("purpose", fromName + "的任务细分已上报");
                    runInsert.put("node", "任务书负责人");
                    break;
                case "20":
                    taskInfo.put("purpose", fromName + "的任务细分已反馈");
                    runInsert.put("purpose", fromName + "的任务细分已反馈");
                    runDelete.put("purpose", fromName + "的任务细分已反馈");
                    param.put("to", "任务拆分负责人");
                    runInsert.put("node", "任务拆分负责人");

                    break;
                case "30":
                    taskInfo.put("purpose", fromName + "的审批结果已返回");
                    runInsert.put("purpose", fromName + "的审批结果已返回");
                    runDelete.put("purpose", fromName + "的审批结果已返回");
                    runInsert.put("node", "任务拆分负责人");
                    break;
                case "40":
                    param.put("taskBookCZExamine", "true");
                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "通过了审批");
                    runInsert.put("purpose", fromName + "通过了审批");
                    runDelete.put("purpose", fromName + "通过了审批");
                    runInsert.put("node", "司长审批(任务书)");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "41":
                    param.put("taskBookCZExamine", "false");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "驳回了审批");
                    runInsert.put("purpose", fromName + "驳回了审批");
                    runDelete.put("purpose", fromName + "驳回了审批");
                    runInsert.put("node", "任务书负责人");
//                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                        taskOpinion.put("content", "不同意");
//                    }
                    break;
                case "50":
                    param.put("taskBookSZExamine", "true");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "通过了审批");
                    runInsert.put("purpose", fromName + "通过了审批");
                    runDelete.put("purpose", fromName + "通过了审批");
                    runInsert.put("node", "任务书负责人");
                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
                        taskOpinion.put("content", "同意");
                    }
                    break;
                case "51":
                    param.put("taskBookSZExamine", "false");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "驳回了审批");
                    runInsert.put("purpose", fromName + "驳回了审批");
                    runDelete.put("purpose", fromName + "驳回了审批");
                    runInsert.put("node", "处长审批(任务书)");
//                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                        taskOpinion.put("content", "不同意");
//                    }
                    break;
            }
            boolean result = false;
            JSONObject claimResult = null;
            JSONArray infoResult = null;

            if("20".equals(type)||"30".equals(type)){
                JSONObject j = new JSONObject();
                j.put("activityId",jsonObject.getString("taskId"));
                JSONObject taskInfoParam = taskInfoService.findTaskInfoByActivityId(j);
                param.put("prevAssignee",taskInfoParam.get("prevAssignee"));
                runInsert.put("prevAssignee",taskInfoParam.get("prevAssignee"));
                runDelete.put("prevAssignee",taskInfoParam.get("prevAssignee"));
            }

            if ("01".equals(type) || "10".equals(type)) {
                claimResult = activitiService.claimCompleteTaskMany(param);
            } else {
                claimResult = activitiService.claimCompleteTask(param);
                if (userList == null || userList.size() < 1) {
//                    param.put("taskStatus", "end");
                    if (!"end".equals(param.get("taskStatus"))) {
//                        logger.info("++++toNext+++358+++++++");
//                        logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + claimResult.getString("assignee"));
//                        logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%数组长度" + claimResult.getString("assignee").split(",").length);
//                        logger.info("++++toNext+++358+++++++");
//                        if("20".equals(type)){
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[1] + "\"}")));
//                        }else if("30".equals(type)){
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[0] + "\"}")));
//                        }
                        userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[0] + "\"}")));

                        executeDetails = "";
//                        logger.info("++++userList++++++++++");
//                        logger.info("!!!!!!!!!!!!!!!!!!!" + userList);
//                        logger.info("++++userList++++++++++");
                        for (JSONObject userJSONObject : userList) {
                            toNext = userJSONObject.getJSONObject("user").getString("userName");
//                            logger.info("++++toNext+++358+++++++");
//                            logger.info("&&&&&&&&&&&&&&&&&&&&&&&" + userJSONObject.getJSONObject("user"));
//                            logger.info("!!!!!!!!!!!!!!!!!!!" + toNext);
//                            logger.info("++++toNext+++358+++++++");
                        }
                        executeDetails += toNext;
                        taskOpinion.put("executeDetails", executeDetails);
                    }
                }
            }
//            if (!"end".equals(param.get("taskStatus"))) {
            //修改
            if (!"90".equals(type) && claimResult.has("ids")) {
                infoResult = claimResult.getJSONArray("ids");
                JSONObject aaa = new JSONObject();
                aaa.put("processInstanceId", jsonObject.getString("processInstanceId"));
                JSONArray jsonArray = activitiService.thisTask(aaa);
            }

//            }
            if("10".equals(type)||"11".equals(type)){
                taskInfo.put("prevAssignee", entity.getUuid());
            }

            if (null != infoResult && infoResult.size() > 0) {
                for (int i = 0; i < infoResult.size(); i++) {
                    taskInfo.put("activityId", infoResult.getString(i));
                    taskInfoService.saveOrUpdate(taskInfo);
                }
            }
            taskInfo.put("activityId", jsonObject.getString("taskId"));
            boolean flag = taskInfoService.saveOrUpdate(taskInfo);
            if (!"90".equals(type)) {
                boolean taskOpinionResult = taskOpinionService.save(taskOpinion);
            }

//            logger.info("+++++添加taskOpinion+++++");
//            logger.info("添加taskOpinion结果   " + taskOpinionResult);
//            logger.info("*************" + taskOpinion.toString());
//            logger.info("+++++添加taskOpinion+++++");
            runDelete.put("userId", fromId);
            runDelete.put("userName", fromName);
            runDelete.put("userPath", fromPath);
            runInsert.put("userId", fromId);
            runInsert.put("userName", fromName);
            runInsert.put("userPath", fromPath);
            runInsert.put("processInstanceId", jsonObject.getString("processInstanceId"));

//            if (!"00".equals(type) && !"".equals(type)) {
            hbUtil.deleteRuntimeInsertHistoric(runDelete);
//            }
            if(!"02".equals(type) && !"".equals(type)){
                hbUtil.updateRuntime(runInsert);
            }

            return ResultGenerator.genSuccessResult(result);
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genSuccessResult(null);
        }
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(@RequestBody JSONObject jsonObject) {
        try {
            String processDefinitionKey = activitiService.deployment().getString("processDefinitionKey");
            JSONObject j = new JSONObject();
            j.put("processDefinitionKey", processDefinitionKey);
            j.put("assignee", jsonObject.getString("assignee"));
            JSONObject result = activitiService.startProcess(j);
            JSONObject taskInfo = new JSONObject();
            String taskId = activitiService.thisTask(result).getJSONObject(0).getString("taskId");
            taskInfo.put("resourceId", jsonObject.getString("taskbookId"));
            taskInfo.put("activityId", result.getString("processInstanceId"));
            String title = jsonObject.getString("title").replace("-", "").replace(":", "").replace(" ", "");
            taskInfo.put("title", "任务书" + title);
            taskInfo.put("purpose", "任务待审批");
//            taskInfo.put("prevAssignee", jsonObject.getString("assignee"));
            taskInfoService.save(taskInfo);
            JSONObject q = new JSONObject();
            q.put("taskId", taskId);
            q.put("processInstanceId", result.getString("processInstanceId"));
            return ResultGenerator.genSuccessResult(q);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genSuccessResult(false);
        }
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public Result execute(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            String fromName = "";
            String fromId = "";
            String fromPath = "";
            String executeDetails = "";
            String toNext = "";
            String x = "";
            String t = "";
            JSONArray tos = new JSONArray();
            List<JSONObject> userList = new ArrayList();

            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                fromName = entity.getUserName();
                fromId = entity.getUuid();
                fromPath = entity.getAllPathName();
            }
            String type = jsonObject.getString("type");
            JSONObject p = new JSONObject();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("taskId", jsonObject.getString("taskId"));
            JSONObject taskInfo = new JSONObject();
            JSONObject taskOpinion = new JSONObject();
            taskOpinion.put("id", UUID.randomUUID().toString());
//            if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                taskOpinion.put("content", "同意");
//            } else {
            taskOpinion.put("content", jsonObject.getString("content"));
//            }
            taskOpinion.put("opinionTime", new Date());
            taskOpinion.put("activityId", jsonObject.getString("taskId"));
            taskOpinion.put("resourceId", jsonObject.getString("taskbookId"));
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                taskOpinion.put("opinionerId", entity.getUuid());
                taskOpinion.put("opinionerName", entity.getUserName());
                taskOpinion.put("opinionerFullpath", entity.getAllPathName());
            }
            taskInfo.put("resourceId", jsonObject.getString("taskbookId"));
            String title = "";
            JSONObject runInsert = new JSONObject();
            JSONObject runDelete = new JSONObject();
            if (jsonObject.getString("title").indexOf(":") > 0) {
                title = jsonObject.getString("title").replace("-", "").replace(":", "").replace(" ", "");
                taskInfo.put("title", "任务书" + title);
                runInsert.put("title", "任务书" + title);
                runDelete.put("title", "任务书" + title);
            } else {
                title = jsonObject.getString("title");
                taskInfo.put("title", "《" + title + "》任务书");
                runInsert.put("title", "《" + title + "》任务书");
                runDelete.put("title", "《" + title + "》任务书");
            }
            runDelete.put("taskId", jsonObject.getString("taskId"));
            switch (type) {
                case "90":
                    t = jsonObject.getString("assignee");
                    param.put("assignee", t);
                    taskInfo.put("purpose", fromName + "的任务未完成");
                    runInsert.put("purpose", fromName + "的任务未完成");
                    runDelete.put("purpose", fromName + "的任务未完成");
                    x = "{\"id\":" + "\"" + t + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = entity.getUserName();
                    taskOpinion.put("executeDetails", executeDetails);
                    break;
                case "00":
                    param.put("taskStatus", "examine");
                    t = jsonObject.getJSONArray("assignee").getString(0);
                    param.put("assignee", t);
                    taskInfo.put("purpose", fromName + "的任务待审批");
                    runInsert.put("purpose", fromName + "的任务待审批");
                    runDelete.put("purpose", fromName + "的任务待审批");
                    runInsert.put("node", "处长审批(任务书)");

                    x = "{\"id\":" + "\"" + t + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "01":
                    param.put("taskStatus", "handout");
                    JSONArray ja = jsonObject.getJSONArray("assignee");
                    JSONArray PersonliableList = new JSONArray();
                    for (int i = 0; i < ja.size(); i++) {
                        String[] paths = ja.getString(i).split("-");
                        String assignees = "";
                        for (String temp : paths) {
                            assignees += this.getUsersByRole(temp, managerCode) + ",";
                        }
                        PersonliableList.add(assignees.substring(0, assignees.length() - 1));
                    }

                    String personId = "";
                    String xxx = "";
                    for (int i = 0; i < PersonliableList.size(); i++) {
                        if (!"".equals(PersonliableList.getString(i))) {
                            xxx = PersonliableList.getString(i).toCharArray()[0] + "";
                            if (",".equals(xxx)) {
                                personId = PersonliableList.getString(i).substring(1, PersonliableList.getString(i).length());
                            } else {
                                personId = PersonliableList.getString(i);
                            }
                            String[] ids = personId.split(",");
                            for (int index = 0; index <= ids.length - 1; index++) {
                                x = "{\"id\":" + "\"" + ids[index] + "\"}";
                                userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                            }
//                            x = "{\"id\":" + "\"" + personId.split(",")[0] + "\"}";
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                        }

                    }
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext += userJSONObject.getJSONObject("user").getString("userName") + "、";
                    }
                    if (!"".equals(toNext)) {
                        toNext = toNext.substring(0, toNext.length() - 1);
                    }
//                    if (userList.size() > 2) {
//                        StringBuilder sb = new StringBuilder(toNext);
//                        sb.replace(toNext.lastIndexOf("、"), toNext.lastIndexOf("、") + 1, "和");
//                        toNext = sb.toString();
//                    } else if (userList.size() > 0 && userList.size() <= 2) {
//                        toNext = toNext.replace("、", "和");
//                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    param.put("PersonliableList", PersonliableList);
                    taskInfo.put("purpose", fromName + "的任务已下发");
                    runInsert.put("purpose", fromName + "的任务已下发");
                    runDelete.put("purpose", fromName + "的任务已下发");
                    runInsert.put("node", "任务拆分负责人");
                    break;
                case "02":
                    param.put("taskStatus", "end");
                    taskInfo.put("purpose", fromName + "的任务已完成");
                    runInsert.put("purpose", fromName + "的任务已完成");
                    runDelete.put("purpose", fromName + "的任务已完成");
                    taskOpinion.put("executeDetails", "");
                    break;
                case "10":
                    param.put("dutyStatus", "split");
                    param.put("SplitPersonList", jsonObject.getJSONArray("assignee"));
                    taskInfo.put("purpose", fromName + "的任务细分已下发");
                    runInsert.put("purpose", fromName + "的任务细分已下发");
                    runDelete.put("purpose", fromName + "的任务细分已下发");
                    runInsert.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runDelete.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runInsert.put("node", "任务拆分");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "11":
                    param.put("dutyStatus", "examine");
                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "的任务细分待审批");
                    runInsert.put("purpose", fromName + "的任务细分待审批");
                    runDelete.put("purpose", fromName + "的任务细分待审批");
                    runInsert.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runDelete.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    runInsert.put("node", "处长审批(任务拆分)");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "12":
                    param.put("dutyStatus", "report");
                    param.put("to", "任务书负责人");
                    taskInfo.put("purpose", fromName + "的任务细分已上报");
                    runInsert.put("purpose", fromName + "的任务细分已上报");
                    runDelete.put("purpose", fromName + "的任务细分已上报");
                    runInsert.put("node", "任务书负责人");
                    break;
                case "20":
                    taskInfo.put("purpose", fromName + "的任务细分已反馈");
                    runInsert.put("purpose", fromName + "的任务细分已反馈");
                    runDelete.put("purpose", fromName + "的任务细分已反馈");
                    param.put("to", "任务拆分负责人");
                    runInsert.put("node", "任务拆分负责人");

                    break;
                case "30":
                    taskInfo.put("purpose", fromName + "的审批结果已返回");
                    runInsert.put("purpose", fromName + "的审批结果已返回");
                    runDelete.put("purpose", fromName + "的审批结果已返回");
                    runInsert.put("node", "任务拆分负责人");
                    break;
                case "40":
                    param.put("taskBookCZExamine", "true");
                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "通过了审批");
                    runInsert.put("purpose", fromName + "通过了审批");
                    runDelete.put("purpose", fromName + "通过了审批");
                    runInsert.put("node", "司长审批(任务书)");

                    tos = jsonObject.getJSONArray("assignee");
                    x = "{\"id\":" + "\"" + tos.getString(0) + "\"}";
                    userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject(x)));
                    executeDetails = "";
                    for (JSONObject userJSONObject : userList) {
                        toNext = userJSONObject.getJSONObject("user").getString("userName");
                    }
                    executeDetails += toNext;
                    taskOpinion.put("executeDetails", executeDetails);

                    break;
                case "41":
                    param.put("taskBookCZExamine", "false");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "驳回了审批");
                    runInsert.put("purpose", fromName + "驳回了审批");
                    runDelete.put("purpose", fromName + "驳回了审批");
                    runInsert.put("node", "任务书负责人");
//                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                        taskOpinion.put("content", "不同意");
//                    }
                    break;
                case "50":
                    param.put("taskBookSZExamine", "true");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "通过了审批");
                    runInsert.put("purpose", fromName + "通过了审批");
                    runDelete.put("purpose", fromName + "通过了审批");
                    runInsert.put("node", "任务书负责人");
                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
                        taskOpinion.put("content", "同意");
                    }
                    break;
                case "51":
                    param.put("taskBookSZExamine", "false");
//                    param.put("assignee", jsonObject.getJSONArray("assignee").getString(0));
                    taskInfo.put("purpose", fromName + "驳回了审批");
                    runInsert.put("purpose", fromName + "驳回了审批");
                    runDelete.put("purpose", fromName + "驳回了审批");
                    runInsert.put("node", "处长审批(任务书)");
//                    if (!jsonObject.has("content") || null == jsonObject.getString("content") || "".equals(jsonObject.getString("content"))) {
//                        taskOpinion.put("content", "不同意");
//                    }
                    break;
            }
            boolean result = false;
            JSONObject claimResult = null;
            JSONArray infoResult = null;


            if("20".equals(type)||"30".equals(type)){
                JSONObject j = new JSONObject();
                j.put("activityId",jsonObject.getString("taskId"));
                JSONObject taskInfoParam = taskInfoService.findTaskInfoByActivityId(j);
                param.put("prevAssignee",taskInfoParam.get("prevAssignee"));
                runInsert.put("prevAssignee",taskInfoParam.get("prevAssignee"));
                runDelete.put("prevAssignee",taskInfoParam.get("prevAssignee"));
            }

            if ("01".equals(type) || "10".equals(type)) {
                claimResult = activitiService.claimCompleteTaskMany(param);
            } else {
                claimResult = activitiService.claimCompleteTask(param);
                if (userList == null || userList.size() < 1) {
//                    param.put("taskStatus", "end");
                    if (!"end".equals(param.get("taskStatus"))) {
//                        logger.info("++++toNext+++358+++++++");
//                        logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + claimResult.getString("assignee"));
//                        logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%数组长度" + claimResult.getString("assignee").split(",").length);
//                        logger.info("++++toNext+++358+++++++");
//                        if("20".equals(type)){
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[1] + "\"}")));
//                        }else if("30".equals(type)){
//                            userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[0] + "\"}")));
//                        }
                        userList.add(baseUserService.selectUserByGUID(JSONObject.fromObject("{\"id\":" + "\"" + claimResult.getString("assignee").split(",")[0] + "\"}")));

                        executeDetails = "";
//                        logger.info("++++userList++++++++++");
//                        logger.info("!!!!!!!!!!!!!!!!!!!" + userList);
//                        logger.info("++++userList++++++++++");
                        for (JSONObject userJSONObject : userList) {
                            toNext = userJSONObject.getJSONObject("user").getString("userName");
//                            logger.info("++++toNext+++358+++++++");
//                            logger.info("&&&&&&&&&&&&&&&&&&&&&&&" + userJSONObject.getJSONObject("user"));
//                            logger.info("!!!!!!!!!!!!!!!!!!!" + toNext);
//                            logger.info("++++toNext+++358+++++++");
                        }
                        executeDetails += toNext;
                        taskOpinion.put("executeDetails", executeDetails);
                    }
                }
            }
//            if (!"end".equals(param.get("taskStatus"))) {
            //修改
            if (!"90".equals(type) && claimResult.has("ids")) {
                infoResult = claimResult.getJSONArray("ids");
                JSONObject aaa = new JSONObject();
                aaa.put("processInstanceId", jsonObject.getString("processInstanceId"));
                JSONArray jsonArray = activitiService.thisTask(aaa);
            }

//            }

            if("10".equals(type)||"11".equals(type)){
                taskInfo.put("prevAssignee", entity.getUuid());
            }

            if (null != infoResult && infoResult.size() > 0) {
                for (int i = 0; i < infoResult.size(); i++) {
                    taskInfo.put("activityId", infoResult.getString(i));
                    /////diudiudiu
                    taskInfoService.saveOrUpdate(taskInfo);
                }
            }
            taskInfo.put("activityId", jsonObject.getString("taskId"));
            boolean flag = taskInfoService.saveOrUpdate(taskInfo);
            if (!"90".equals(type)) {
                boolean taskOpinionResult = taskOpinionService.save(taskOpinion);
            }

//            logger.info("+++++添加taskOpinion+++++");
//            logger.info("添加taskOpinion结果   " + taskOpinionResult);
//            logger.info("*************" + taskOpinion.toString());
//            logger.info("+++++添加taskOpinion+++++");
            runDelete.put("userId", fromId);
            runDelete.put("userName", fromName);
            runDelete.put("userPath", fromPath);
            runInsert.put("userId", fromId);
            runInsert.put("userName", fromName);
            runInsert.put("userPath", fromPath);
            runInsert.put("processInstanceId", jsonObject.getString("processInstanceId"));

//            if (!"00".equals(type) && !"".equals(type)) {
            hbUtil.deleteRuntimeInsertHistoric(runDelete);
//            }
            if(!"02".equals(type) && !"".equals(type)){
                hbUtil.updateRuntime(runInsert);
            }
            return ResultGenerator.genSuccessResult(result);
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genSuccessResult(null);
        }
    }

    @RequestMapping(value = "/historicTasks", method = RequestMethod.POST)
    public Result historicTasks(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = (ThrInOneEntity) session.getAttribute("currentUser");
            JSONObject p = new JSONObject();
            p.put("userId", entity.getUuid());
            Integer page = 0;
            Integer size = 10;
            if (jsonObject.has("page") && null != jsonObject.get("page")) {
                page = jsonObject.getInt("page");
            }
            if (jsonObject.has("size") && null != jsonObject.get("size")) {
                size = jsonObject.getInt("size");
            }
            p.put("page", page);
            p.put("size", size);
            JSONObject result = activitiService.historicTasks(p);
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject temp = list.getJSONObject(i);
                JSONObject param = new JSONObject();
                param.put("activityId", temp.getString("taskId"));
                JSONObject taskInfo = taskInfoService.getById(param);
                taskInfo.remove("activityId");
                temp.putAll(taskInfo);
            }
            return ResultGenerator.genSuccessResult(result);
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/runtimeTasks", method = RequestMethod.POST)
    public Result runtimeTasks(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = (ThrInOneEntity) session.getAttribute("currentUser");
            JSONObject p = new JSONObject();
            p.put("userId", entity.getUuid());
            Integer page = 0;
            Integer size = 10;
            if (jsonObject.has("page") && null != jsonObject.get("page")) {
                page = jsonObject.getInt("page");
            }
            if (jsonObject.has("size") && null != jsonObject.get("size")) {
                size = jsonObject.getInt("size");
            }
            p.put("page", page);
            p.put("size", size);
//            p.put("page",jsonObject.getString("page"));
//            p.put("size",jsonObject.getString("size"));
            JSONObject result = activitiService.runtimeTasks(p);
            JSONArray list = result.getJSONArray("list");
            List<Integer> rm = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                JSONObject temp = list.getJSONObject(i);
                JSONObject param = new JSONObject();
                JSONObject status = new JSONObject();
                param.put("activityId", temp.getString("taskId"));
                JSONObject taskInfo = taskInfoService.getById(param);
                taskInfo.remove("activityId");
                if (taskInfo.has("resourceId")) {
                    String resourceId = "{\"resourceId\":" + "\"" + taskInfo.getString("resourceId") + "\"}";
                    JSONObject taskOpinionResult = taskOpinionService.selectOpinionByTaskId(JSONObject.fromObject(resourceId));
                    if ("0".equals(taskOpinionResult.getString("result"))) {
                        temp.put("status", "4");
                    }
                }
                temp.putAll(taskInfo);
            }
            return ResultGenerator.genSuccessResult(result);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/getAuth", method = RequestMethod.POST)
    public Result getAuth(@RequestBody JSONObject jsonObject) {
        try {
            String node = "";
            if (jsonObject.has("node")) {
                node = jsonObject.getString("node");
            }
            JSONObject param = new JSONObject();
            JSONObject variables = null;
            Object temp = jsonObject.get("variables");
            if (null != temp && !"".equals(temp)) {
                variables = (JSONObject) temp;
            }
            param.put("node", node);
            JSONObject auth = taskAuthService.getByNode(param);
            JSONArray type = new JSONArray();
            if ("任务书负责人".equals(node)) {
                if (null != variables && variables.has("taskBookSZExamine") && "true".equals(variables.getString("taskBookSZExamine"))) {
                    JSONArray auths = JSONArray.fromObject(auth.getString("scene"));
                    for (int i = 0; i < auths.size(); i++) {
                        JSONObject t = auths.getJSONObject(i);
                        if (t.getString("id").equals("taskBook")) {
                            t.put("readOnly", true);
                        }
                        if (t.getString("id").equals("system")) {
                            t.put("readOnly", true);
                        }
                    }
                    auth.put("scene", auths);
                }
                if (null == variables || !variables.has("taskBookSZExamine") || "false".equals(variables.getString("taskBookSZExamine"))) {
                    type.add("00");
                } else if (variables.has("dutyStatus") && "report".equals(variables.getString("dutyStatus"))) {
                    type.add("01");
                    type.add("02");
                } else if ("true".equals(variables.getString("taskBookSZExamine"))) {
                    type.add("01");
                } else {
                    type.add("00");
                    type.add("01");
                    type.add("02");
                }
                auth.put("type", type);
            }
            return ResultGenerator.genSuccessResult(auth);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "/getParent", method = RequestMethod.POST)
    public Result getParent(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = (ThrInOneEntity) session.getAttribute("currentUser");
            String[] paths = entity.getAllPathName().split("\\\\");
            String parentPath = new String();
            for (int i = 0; i < paths.length; i++) {
                parentPath += paths[i];
            }
            JSONObject params = new JSONObject();
            JSONArray childs = custService.getTree(params);
            return ResultGenerator.genSuccessResult(parentPath);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
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
