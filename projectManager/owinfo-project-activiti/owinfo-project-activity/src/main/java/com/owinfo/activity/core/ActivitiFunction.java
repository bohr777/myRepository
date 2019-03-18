package com.owinfo.activity.core;

import com.owinfo.activity.util.ProcessInstanceDiagram;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * Created by liyue on 2017/6/29.
 * <p>
 * 提供Activiti常用方法  此类是直接跟activiti服务进行交互
 * 比如 ： 查询列表 ， 完成任务， 分配任务......
 * 避免在开发中多次编写重复的操作
 * 这个类统一进入Activiti接口的注入  Service类中尽量不要重复进行接口的注入
 * <p>
 * version 3.0
 */
@Component
public class ActivitiFunction implements Serializable {


    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;

    public long runtimeCount(String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        return taskQuery.taskAssigneeLike("%" + userId + "%").count();
    }

//    public List<Task> getVariables(String processInstanceId){
//        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//        return list;
//    }

    public long historicCount(String userId) {


        HistoricTaskInstanceQuery hiTaskQuery = historyService.createHistoricTaskInstanceQuery();
        return hiTaskQuery.taskAssigneeLike("%" + userId + "%").finished().count();
    }

    /**
     * 完成任务  传入任务ID
     *
     * @param taskId
     */
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    /**
     * 完成任务  传入任务ID 可以附带流程变量
     *
     * @param taskId
     * @param map
     */
    public void completeTask(String taskId, Map<String, Object> map) {
        taskService.complete(taskId, map);
    }

    /**
     * 获取当前执行的任务的执行对象ID
     *
     * @param taskId
     * @return
     */
    public String executionId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String executionId = task.getExecutionId();
        return executionId;
//        List<Execution> executions = runtimeService.createExecutionQuery().executionId(executionId).list();
//        for (Execution execution : executions) {
//            String activityId = execution.getActivityId();
//            if(activityId != null){
//                Task result = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
//                return result.getId();
//            }
//        }
//        return null;
    }

    public List<Task> getTasks(String processInstanceId, String executionId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).executionId(executionId).list();
    }

    public List<Task> getTasks(String processInstanceId, String name, String assignee) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.processInstanceId(processInstanceId).taskName(name);
        if (assignee != null) {
//            taskQuery.taskAssignee(assignee);
            taskQuery.taskAssigneeLike("%" + assignee + "%");
        }
        return taskQuery.list();
    }

    public List<Task> getTasks(String processInstanceId, String name, boolean flag) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskName(name).list();
    }

    public List<Task> getTasks(String processInstanceId, String name, String taskDefinitionKey, boolean flag) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskName(name).taskDefinitionKey(taskDefinitionKey).list();
    }

    public List<Task> getTasks(String processInstanceId, List<String> assignees) {
        List<Task> tasks = new ArrayList();
        TaskQuery taskQuery = taskService.createTaskQuery();
        for (String assignee : assignees) {
            tasks.addAll(taskQuery.processInstanceId(processInstanceId).taskAssignee(assignee).list());
        }
        return tasks;
    }

    /**
     * 获取当前执行的任务的流程实例ID
     *
     * @param taskId
     * @return
     */
    public String processInstanceId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        return processInstanceId;
//        List<Execution> executions = runtimeService.createExecutionQuery().executionId(executionId).list();
//        for (Execution execution : executions) {
//            String activityId = execution.getActivityId();
//            if(activityId != null){
//                Task result = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
//                return result.getId();
//            }
//        }
//        return null;
    }

    public Task thisTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }


    /**
     * 获取当前执行的任务(执行对象中获取 保证唯一)
     *
     * @param executionId
     * @return
     */
    public Task thisTask(String executionId, String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        if (execution != null) {
            Task task = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
            return task;
        } else {
            /**
             * 获取现在这个实例的执行对象个数
             */
            List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
            /**
             * 去除流程实例本身
             */
            Iterator<Execution> iterator = list.iterator();
            while (iterator.hasNext()) {
                Execution ex = iterator.next();
                if (ex.getParentId() == null) {
                    iterator.remove();
                }
            }
            //执行对象大于一个表示节点还处于多实例中
            if (list.size() != 1) {
                //不用设置
                return null;
            } else {
                //任务已回归单实例
                Execution ex = list.get(0);
                Task task = taskService.createTaskQuery().executionId(ex.getId()).singleResult();
                return task;
            }
        }
    }

    /**
     * 改变任务办理人 如果任务没有办理人 则拾取
     *
     * @param taskId
     * @param assignee
     */
    public void changeAssignee(String taskId, String assignee) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task.getAssignee() == null) {
            taskService.claim(taskId, assignee);
        } else {
            task.setAssignee(assignee);
        }
    }

    /**
     * 获取某个用户的任务列表
     *
     * @param userId
     * @return
     */
    public List<Task> runtimeTasks(String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        return taskQuery.taskAssigneeLike("%" + userId + "%").orderByTaskCreateTime().desc().list();
    }

    public List<Task> runtimeTasks(String userId, int from, int to) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        return taskQuery.taskAssigneeLike("%" + userId + "%").orderByTaskCreateTime().desc().listPage(from, to);
    }

    public List<Task> runtimeTasks(String processInstanceId, String node,String assignee) {
//        TaskQuery taskQuery = taskService.createTaskQuery();
//        return taskQuery.processInstanceId(processInstanceId).taskName(node).list();

        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.processInstanceId(processInstanceId).taskName(node);
        if (!"".equals(assignee)) {
            taskQuery.taskAssigneeLike("%" + assignee + "%");
        }
        return taskQuery.list();
    }

    public List<Task> runtimeTasks(String taskId, boolean flag) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        return taskQuery.taskId(taskId).list();
    }

    /**
     * 获取某个用户的历史任务列表
     *
     * @param userId
     * @return
     */
    public List<HistoricTaskInstance> historicTasks(String userId) {
        HistoricTaskInstanceQuery hiTaskQuery = historyService.createHistoricTaskInstanceQuery();
        return hiTaskQuery.taskAssigneeLike("%" + userId + "%").finished().orderByHistoricTaskInstanceEndTime().desc().list();
    }

    public List<HistoricTaskInstance> historicTasks(String userId, int from, int to) {
        HistoricTaskInstanceQuery hiTaskQuery = historyService.createHistoricTaskInstanceQuery();
        return hiTaskQuery.taskAssigneeLike("%" + userId + "%").finished().orderByHistoricTaskInstanceEndTime().desc().listPage(from, to);
    }

    public List<HistoricTaskInstance> historicTasks(String taskId, boolean flag) {
        HistoricTaskInstanceQuery hiTaskQuery = historyService.createHistoricTaskInstanceQuery();
        return hiTaskQuery.taskId(taskId).finished().list();
    }

    public List<HistoricTaskInstance> historicTasks(String processInstanceId, String node) {
        HistoricTaskInstanceQuery hiTaskQuery = historyService.createHistoricTaskInstanceQuery();
        return hiTaskQuery.processInstanceId(processInstanceId).taskName(node).finished().list();
    }

    public String getAssignee(String processInstanceId, String define) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        return historicTaskInstanceQuery.processInstanceId(processInstanceId).taskDefinitionKey(define).finished().list().get(0).getAssignee();
    }

    public List<HistoricTaskInstance> getAssigneeNew(String processInstanceId, String define) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        return historicTaskInstanceQuery.processInstanceId(processInstanceId).taskDefinitionKey(define).finished().list();
    }


    /**
     * 当前执行的任务(整个流程中的任务)
     *
     * @param processInstanceId
     * @return
     */
    public List<Task> activityTask(String processInstanceId) {
        List<Task> list = new ArrayList<>();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        for (Execution execution : executions) {
            String activityId = execution.getActivityId();
            if (activityId != null) {
                Task task = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
                if (task != null) {
                    list.add(task);
                }
            }
        }
        return list;
    }

    /**
     * 获取流程实时流程图
     *
     * @param processInstanceId
     * @return
     */
    public byte[] activitiProccessImage(String processInstanceId) {
        return new ProcessInstanceDiagram(processInstanceId,
                repositoryService,
                processEngine,
                historyService)
                .getActivitiProccessImage();
    }

    /**
     * 获取单个流程变量  执行对象中取
     *
     * @param taskId
     * @param variableName
     * @return
     */
    public Object variableSingle(String taskId, String variableName) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return runtimeService.getVariable(task.getExecutionId(), variableName);
    }

    /**
     * 获取全部的流程变量
     *
     * @param executionId
     * @return
     */
    public Map<String, Object> variables(String executionId) {
        return runtimeService.getVariables(executionId);
    }

    /**
     * 获取历史流程变量
     *
     * @param processInstanceId
     * @return
     */
    public Map<String, Object> hiVariables(String processInstanceId) {
        List<HistoricVariableInstance> historicVariableInstances =
                historyService.createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .list();
        Map<String, Object> variables = new HashMap<String, Object>();
        for (HistoricVariableInstance historicVariableInstance :
                historicVariableInstances) {
            variables.put(historicVariableInstance.getVariableName(),
                    historicVariableInstance.getValue());
        }
        return variables;
    }

    /**
     * 根据流程定义ID启动流程
     *
     * @param processDefinitionId
     */
    public ProcessInstance startProcess(String processDefinitionId, String assignee) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionId);
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        for (Execution execution : list) {
            String activityId = execution.getActivityId();
            if (activityId != null) {
                Task task = taskService.createTaskQuery().executionId(execution.getId()).singleResult();
                this.changeAssignee(task.getId(), assignee);
            }
        }
        return processInstance;
    }

    /**
     * 部署
     *
     * @return
     */
    public ProcessDefinition deployment() {
        Deployment deploy = repositoryService
                .createDeployment()
                .name("任务书")
                .category("LIYUE")
                .addClasspathResource("bpmn/TaskProcess.bpmn")
                .addClasspathResource("bpmn/TaskProcess.png")
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        return processDefinition;
    }

    /**
     * 查询部署
     */
    public List<ProcessDefinition> queryDeployment() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("TaskBookProcess")
                .latestVersion()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        return processDefinitions;
    }

    /**
     * 删除部署
     */
    public void removeDeployment(String deploymentId, Boolean cascade) {
        if (cascade == null) {
            repositoryService.deleteDeployment(deploymentId);
            return;
        }
        repositoryService.deleteDeployment(deploymentId, cascade);
    }
}
