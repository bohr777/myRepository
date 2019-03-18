package com.owinfo.activity.service;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.owinfo.activity.core.ActivitiFunction;
import com.owinfo.activity.core.TaskInfoService;
import com.owinfo.activity.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by liyue on 2017/12/25.
 */
@RestController
@RequestMapping("/act")
public class ActivitiController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiFunction function;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskInfoService taskInfoService;

    @RequestMapping(value = "/getVariables", method = RequestMethod.POST)
    public Map<String, Object> getVariables(@RequestBody JSONObject jsonObject) {
        Map<String, Object> variablesList = function.hiVariables(jsonObject.getString("processInstanceId"));
        return variablesList;
    }

    @RequestMapping(value = "/getProcessInstanceId", method = RequestMethod.POST)
    public List<Task> getProcessInstanceId(@RequestBody JSONObject jsonObject) {
        List<Task> taskList = function.runtimeTasks(jsonObject.getString("taskId"), true);
        return taskList;
    }


    /**
     * 用户待办
     * 用户ID
     *
     * @return
     */
    @RequestMapping(value = "/runtimeTasks", method = RequestMethod.POST)
    public JSONObject runtimeTasks(@RequestBody JSONObject jsonObject) {
        try {
            int from = 0;
            int to = 10;
            if (jsonObject.has("page")) {
                from = jsonObject.getInt("page") * jsonObject.getInt("size");
            }
            if (jsonObject.has("size")) {
                to = jsonObject.getInt("size");
            }
            List<Task> tasks = null;
            JSONObject result = new JSONObject();
            JSONArray list = new JSONArray();
            JSONObject param = new JSONObject();
            if (jsonObject.has("processInstanceId")) {
                if (jsonObject.has("node")) {
//                    String executionId = "";
//                    if (jsonObject.has("executionId")) {
//                        executionId = jsonObject.getString("executionId");
//                    }
                    String assignee = "";
                    if (jsonObject.has("prevAssignee")) {
                        assignee = jsonObject.getString("prevAssignee");
                    }
                    if (jsonObject.has("assignee")) {
                        assignee = jsonObject.getString("assignee");
                    }
                    tasks = function.runtimeTasks(jsonObject.getString("processInstanceId"), jsonObject.getString("node"),assignee);
                    for (Task task : tasks) {
                        JSONObject object = new JSONObject();
                        object.put("taskId", task.getId());

                        param.put("activityId", task.getId());
                        JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                        if (null != taskInfo) {
                            object.put("taskbookId", taskInfo.get("taskbookId"));
                            object.put("purpose", taskInfo.get("purpose"));
                            object.put("title", taskInfo.get("title"));
                        }

                        object.put("taskName", task.getName());
                        object.put("assignee", task.getAssignee());
                        object.put("createTime", task.getCreateTime().getTime());
                        object.put("executionId", task.getExecutionId());

                        object.put("processDefinitionId", task.getProcessDefinitionId());

                        object.put("processInstanceId", task.getProcessInstanceId());
                        object.put("variables", function.variables(task.getExecutionId()));
                        list.add(object);
                    }
                    result.put("list", list);
                }
            } else if (jsonObject.has("taskId")) {
                tasks = function.runtimeTasks(jsonObject.getString("taskId"), true);
                for (Task task : tasks) {
                    JSONObject object = new JSONObject();
                    object.put("taskId", task.getId());
                    param.put("activityId", task.getId());
                    JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                    if (null != taskInfo) {
                        object.put("taskbookId", taskInfo.get("taskbookId"));
                        object.put("purpose", taskInfo.get("purpose"));
                        object.put("title", taskInfo.get("title"));
                    }

                    object.put("taskName", task.getName());
                    object.put("assignee", task.getAssignee());
                    object.put("createTime", task.getCreateTime().getTime());
                    object.put("executionId", task.getExecutionId());

                    object.put("processDefinitionId", task.getProcessDefinitionId());

                    object.put("processInstanceId", task.getProcessInstanceId());
                    object.put("variables", function.variables(task.getExecutionId()));
                    list.add(object);
                }
                result.put("list", list);
            } else {
                tasks = function.runtimeTasks(jsonObject.getString("userId"), from, to);
                for (Task task : tasks) {
                    JSONObject object = new JSONObject();
                    object.put("taskId", task.getId());

                    param.put("activityId", task.getId());
                    JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                    if (null != taskInfo) {
                        object.put("taskbookId", taskInfo.get("taskbookId"));
                        object.put("purpose", taskInfo.get("purpose"));
                        object.put("title", taskInfo.get("title"));
                    }

                    object.put("taskName", task.getName());
                    object.put("assignee", task.getAssignee());
                    object.put("createTime", task.getCreateTime());
                    object.put("executionId", task.getExecutionId());

                    object.put("processDefinitionId", task.getProcessDefinitionId());

                    object.put("processInstanceId", task.getProcessInstanceId());
                    object.put("variables", function.variables(task.getExecutionId()));
                    list.add(object);
                }
                result.put("list", list);
                result.put("total", function.runtimeCount(jsonObject.getString("userId")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 用户已办
     * 用户ID
     *
     * @return
     */
    @RequestMapping(value = "/historicTasks", method = RequestMethod.POST)
    public JSONObject historicTasks(@RequestBody JSONObject jsonObject) {
        try {
            int from = 0;
            int to = 10;
            if (jsonObject.has("page")) {
                from = jsonObject.getInt("page") * jsonObject.getInt("size");
            }
            if (jsonObject.has("size")) {
                to = jsonObject.getInt("size");
            }
            JSONObject result = new JSONObject();
            JSONArray list = new JSONArray();
            List<HistoricTaskInstance> tasks = null;
            JSONObject param = new JSONObject();
            if (jsonObject.has("processInstanceId")) {
                tasks = function.historicTasks(jsonObject.getString("processInstanceId"), jsonObject.getString("node"));
                for (HistoricTaskInstance task : tasks) {
                    JSONObject object = new JSONObject();
                    object.put("taskId", task.getId());

                    param.put("activityId", task.getId());
                    JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                    if (null != taskInfo) {
                        object.put("taskbookId", taskInfo.get("taskbookId"));
                        object.put("purpose", taskInfo.get("purpose"));
                        object.put("title", taskInfo.get("title"));
                    }

                    object.put("taskName", task.getName());
                    object.put("createTime", task.getCreateTime().getTime());
                    object.put("endTime", task.getEndTime().getTime());
//                    object.put("executionId", task.getExecutionId());
                    object.put("processInstanceId", task.getProcessInstanceId());
//                    object.put("workTimeInMillis", task.getWorkTimeInMillis());
//                    object.put("processVariables", task.getProcessVariables());
                    object.put("assignee", task.getAssignee());
//                    object.put("variables", function.hiVariables(task.getProcessInstanceId()));
                    list.add(object);
                }
                result.put("list", list);
            } else if (jsonObject.has("taskId")) {
                tasks = function.historicTasks(jsonObject.getString("taskId"), true);
                for (HistoricTaskInstance task : tasks) {
                    JSONObject object = new JSONObject();
                    object.put("taskId", task.getId());

                    param.put("activityId", task.getId());
                    JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                    if (null != taskInfo) {
                        object.put("taskbookId", taskInfo.get("taskbookId"));
                        object.put("purpose", taskInfo.get("purpose"));
                        object.put("title", taskInfo.get("title"));
                    }

                    object.put("taskName", task.getName());
                    object.put("assignee", task.getAssignee());
                    object.put("createTime", task.getCreateTime().getTime());
                    object.put("executionId", task.getExecutionId());
                    object.put("processInstanceId", task.getProcessInstanceId());
//                    object.put("variables", function.variables(task.getExecutionId()));
                    list.add(object);
                }
                result.put("list", list);
            } else {
                tasks = function.historicTasks(jsonObject.getString("userId"), from, to);
                for (HistoricTaskInstance task : tasks) {
                    JSONObject object = new JSONObject();
                    object.put("taskId", task.getId());

                    param.put("activityId", task.getId());
                    JSONObject taskInfo = taskInfoService.findTaskInfoByActivityId(param);
                    if (null != taskInfo) {
                        object.put("taskbookId", taskInfo.get("taskbookId"));
                        object.put("purpose", taskInfo.get("purpose"));
                        object.put("title", taskInfo.get("title"));
                    }

                    object.put("taskName", task.getName());
                    object.put("createTime", task.getCreateTime());
                    object.put("endTime", task.getEndTime());
//                    object.put("executionId", task.getExecutionId());
                    object.put("processInstanceId", task.getProcessInstanceId());
//                    object.put("workTimeInMillis", task.getWorkTimeInMillis());
//                    object.put("processVariables", task.getProcessVariables());
                    object.put("assignee", task.getAssignee());
//                    object.put("variables", function.hiVariables(task.getProcessInstanceId()));
                    list.add(object);
                }
                result.put("list", list);
                result.put("total", function.historicCount(jsonObject.getString("userId")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 办理任务 附带流程变量 指定下一个任务的办理人
     *
     * @param param
     * @return
     */

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/claimCompleteTask", method = RequestMethod.POST)
    public JSONObject claimCompleteTask(@RequestBody Map<String, Object> param) {
        try {
            String taskId = (String) param.get("taskId");
            String assignee = (String) param.get("assignee");
//            assignee = assignee.split("-")[0];
            param.remove("taskId");
            param.remove("assignee");
            String executionId = function.executionId(taskId);
            String processInstanceId = function.processInstanceId(taskId);
            if (param.size() > 0) {
                for (String key : param.keySet()) {
                    if ("to".equals(key)) {
                    }
                    if ("taskBookSZExamine".equals(key)) {
                        if ("true".equals(param.get("taskBookSZExamine"))) {
                            assignee = function.getAssignee(processInstanceId, "TaskIssued");
                        } else {
                            assignee = function.getAssignee(processInstanceId, "taskBookChuZhang");
                        }
                    } else if ("taskBookCZExamine".equals(key)) {
                        if ("false".equals(param.get("taskBookCZExamine"))) {
                            assignee = function.getAssignee(processInstanceId, "TaskIssued");
                        }
                    } else if ("dutyStatus".equals(key)) {
                        if ("report".equals(param.get("dutyStatus"))) {
                            assignee = function.getAssignee(processInstanceId, "TaskIssued");
                        }
                    }
                }
                function.completeTask(taskId, param);
            } else {
                function.completeTask(taskId);
            }
            Task thisTask = function.thisTask(executionId, processInstanceId);
            if (thisTask != null) {
                function.changeAssignee(thisTask.getId(), assignee);
            }
            JSONObject result = new JSONObject();
            result.put("assignee", assignee);
            //            logger.info("+++++++244+++assignee+++++");
//            logger.info("!!!!!!!!!!!!!" + assignee);
//            logger.info("+++++++244+++assignee+++++");
            JSONArray resultArray = new JSONArray();
            List<Task> resultTask = null;
            if (null != param.get("to") && !"".equals(param.get("to"))) {
                if ("任务书负责人".equals(param.get("to"))) {
                    resultTask = new ArrayList();
                    resultTask.add(thisTask);
//                    result.put("assignee",thisTask.getAssignee());
                } else if ("任务拆分负责人".equals(param.get("to"))) {
//                    logger.info("+++++resultTask");

                    resultTask = function.getTasks(processInstanceId, "任务拆分负责人", param.get("prevAssignee").toString());
                    logger.info(resultTask.toString());
                    Task task = null;
                    Integer whichOne = resultTask.size();
                    task = function.thisTask(resultTask.get(whichOne - 1).getId());
//                    if (resultTask.size() > 1) {
//                        task = function.thisTask(resultTask.get(1).getId());
//                    } else if (resultTask.size() == 1) {
//                        task = function.thisTask(resultTask.get(0).getId());
//                    }

//                    logger.info("+++++任务拆分负责人+++task++++++++++");
//                    logger.info(task.toString());
//                    logger.info("+++++任务拆分负责人+++task++++++++++");
                    if (task != null) {
                        result.put("assignee", task.getAssignee());
                    }


//                    if(task.getAssignee()==null||"".equals(task.getAssignee())){
//                        HistoricTaskInstance task1 = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//                        String e = task1.getExecutionId();
//                        String p = task1.getProcessInstanceId();
//                        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(p).executionId(e).taskDefinitionKey("Personliable").list();
//                        if(list!=null&&list.size()>0){
//                            result.put("assignee",list.get(0).getAssignee());
//                        }
//                        for (HistoricTaskInstance historicTaskInstance : list) {
//                        }
//                    }


//                    logger.info("+++++任务拆分负责人+++assignee++++++++++");
//                    logger.info(task.getAssignee());
//                    logger.info("+++++任务拆分负责人+++assignee++++++++++");

//                    resultTask = function.getTasks(processInstanceId,"任务拆分负责人","a180256c-f9aa-9d1d-4885-6ee8b46ec480");
                } else {
                    resultTask = function.getTasks(processInstanceId, "任务拆分负责人", true);
                }
            } else {
                resultTask = function.getTasks(processInstanceId, executionId);
                if (resultTask.size() > 0) {
                    resultArray.add(resultTask.get(0).getAssignee());
                    result.put("ids", resultArray);
                    result.put("assignee", resultTask.get(0).getAssignee());
                }

//                logger.info("+++++assignee++++278+++++++");
//                logger.info("!!!!!!!!!!!!!!!!!" + resultTask.get(0).getAssignee());
//                logger.info("+++++assignee++++278+++++++");
            }
            for (Task task : resultTask) {
                if (task != null) {
                    resultArray.add(task.getId());
                }
            }
            result.put("ids", resultArray);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 办理任务 并启动多个子流程 指定多个子流程办理人
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/claimCompleteTaskMany", method = RequestMethod.POST)
    public JSONObject claimCompleteTaskMany(@RequestBody Map<String, Object> param) {
        try {
            String taskId = (String) param.get("taskId");
            List assignees = null;
            for (String key : param.keySet()) {
                if ("PersonliableList".equals(key) || "SplitPersonList".equals(key)) {
                    assignees = (List) param.get(key);
                }
            }
            param.remove("taskId");
            String processInstanceId = function.processInstanceId(taskId);
            if (param.size() > 0) {
                System.out.println("参数是：      "+taskId+"     "+param);
                logger.error("参数是：      "+taskId+"     "+param);
                function.completeTask(taskId, param);
            } else {
                function.completeTask(taskId);
            }
            JSONObject result = new JSONObject();
            result.put("assignee", assignees);
            JSONArray resultArray = new JSONArray();
            List<Task> resultTask = function.getTasks(processInstanceId, assignees);
            for (Task task : resultTask) {
                resultArray.add(task.getId());
            }
            result.put("ids", resultArray);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 获取当前正在执行的任务
     *
     * @return
     */
    @RequestMapping(value = "/thisTask", method = RequestMethod.POST)
    public JSONArray thisTask(@RequestBody JSONObject jsonObject) {
        try {
            String processInstanceId = jsonObject.getString("processInstanceId");
            List<Task> task = function.activityTask(processInstanceId);
            JSONArray list = new JSONArray();
            for (Task temp : task) {
                JSONObject object = new JSONObject();
                object.put("taskId", temp.getId());
                object.put("taskName", temp.getName());
                object.put("createTime", temp.getCreateTime());
                object.put("executionId", temp.getExecutionId());
                object.put("processInstanceId", temp.getProcessInstanceId());
                object.put("assignee", temp.getAssignee());
                object.put("variables", function.variables(temp.getExecutionId()));
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 启动流程 指定任务办理人
     *
     * @return
     */
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public JSONObject startProcess(@RequestBody JSONObject jsonObject) {
        try {
            String processDefinitionKey = jsonObject.getString("processDefinitionKey");
            String assignee = jsonObject.getString("assignee");
            ProcessInstance processInstance = function.startProcess(processDefinitionKey, assignee);
            JSONObject result = new JSONObject();
            result.put("processInstanceId", processInstance.getId());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 获取单个流程变量
     *
     * @return
     */
    @RequestMapping(value = "/variableSingle", method = RequestMethod.POST)
    public Object variableSingle(@RequestBody JSONObject jsonObject) {
        try {
            String taskId = jsonObject.getString("taskId");
            String variableName = jsonObject.getString("variableName");
            Object variable = function.variableSingle(taskId, variableName);
            return variable;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return Result.error(e.toString());
        }
    }

    /**
     * 获取实时流程图
     *
     * @param processInstanceId
     */
    @RequestMapping(value = "/activitiProccessImage", method = RequestMethod.POST)
    public byte[] activitiProccessImage(@RequestParam("processInstanceId") String processInstanceId) {
        try {
            return function.activitiProccessImage(processInstanceId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 部署
     *
     * @return
     */
    @RequestMapping(value = "/deployment", method = RequestMethod.POST)
    public JSONObject deployment() {
        try {
            ProcessDefinition processDefinition = function.deployment();
            JSONObject result = new JSONObject();
            result.put("processDefinitionKey", processDefinition.getKey());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 查询部署的流程
     *
     * @return
     */
    @RequestMapping(value = "/queryDeployment", method = RequestMethod.POST)
    public JSONArray queryDeployment() {
        try {
            List<ProcessDefinition> processDefinitions = function.queryDeployment();
            JSONArray list = new JSONArray();
            for (ProcessDefinition temp : processDefinitions) {
                JSONObject object = new JSONObject();
                object.put("processDefinitionId", temp.getId());
                object.put("processDefinitionName", temp.getName());
                object.put("processDefinitionKey", temp.getKey());
                object.put("processDefinitionVersion", temp.getVersion());
                object.put("deploymentId", temp.getDeploymentId());
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    /**
     * 删除部署的流程
     *
     * @return
     */
    @RequestMapping(value = "/removeDeployment", method = RequestMethod.POST)
    public boolean removeDeployment(@RequestParam("deploymentId") String deploymentId,
                                    @RequestParam(value = "cascade", required = false) Boolean cascade) {
        try {
            function.removeDeployment(deploymentId, cascade);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
    }

    @RequestMapping(value = "/getTaskManagerAssignee", method = RequestMethod.POST)
    public JSONObject getTaskManagerAssignee(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject assignees = new JSONObject();
            String processInstanceId = jsonObject.getString("processInstanceId");
//            List<Task> tasks = function.getTasks(processInstanceId, "任务拆分负责人","Personliable", false);
            String result = "";
            List<HistoricTaskInstance> historicTaskInstances = function.getAssigneeNew(processInstanceId, "Personliable");
            for (HistoricTaskInstance historic : historicTaskInstances) {
                result += historic.getAssignee() + ";";
            }

            List<HistoricTaskInstance> historicTaskInstances1 = function.getAssigneeNew(processInstanceId, "SplitPerson");
            for (HistoricTaskInstance historic : historicTaskInstances1) {
                result += historic.getAssignee() + ";";
            }
            assignees.put("assignees", result);
            return assignees;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "/getVariable", method = RequestMethod.POST)
    public Map<String, Object> getVariable(@RequestBody JSONObject jsonObject) {
        String executionId = jsonObject.getString("executionId");
        return runtimeService.getVariables(executionId);
    }

}
