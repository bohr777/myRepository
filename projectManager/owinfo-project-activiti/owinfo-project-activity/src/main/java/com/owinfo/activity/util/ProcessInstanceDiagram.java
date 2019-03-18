package com.owinfo.activity.util;


import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liyue on 2017/7/4.
 *
 *  获取实时动态流程图
 *    会同时高亮显示正在执行的任务和历史执行任务
 *    由于6.0版本删除了pvm包 需要重构
 */
public class ProcessInstanceDiagram {

    protected String processInstanceId;

    private RepositoryService repositoryService;

    private ProcessEngine processEngine;

    private HistoryService historyService;

    public ProcessInstanceDiagram(String processInstanceId, RepositoryService repositoryService, ProcessEngine processEngine, HistoryService historyService) {
        this.processInstanceId = processInstanceId;
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
        this.historyService = historyService;
    }

    /**
     * 获取流程图像，已执行节点和流程线高亮显示
     */
    public byte[] getActivitiProccessImage() {
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            if (historicProcessInstance == null) {
                //throw new BusinessException("获取流程实例ID[" + processInstanceId + "]对应的历史流程实例失败！");
            }
            else {
                // 获取流程定义
//                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .orderByHistoricActivityInstanceId()
                        .asc()
                        .list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = historicActivityInstanceList
                        .stream()
                        .map(HistoricActivityInstance::getActivityId)
                        .collect(Collectors.toList());
                BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
                // 已执行的线集合
                List<String> flowIds = new ArrayList<String>();
                // 获取流程走过的线
                flowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);

                // 获取流程图图像字符流
                ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
                //配置字体
                InputStream imageStream = pec.generateDiagram(
                        bpmnModel,
                        "png",
                        executedActivityIdList,
                        flowIds,
                        "宋体",
                        "微软雅黑",
                        "黑体",
                        null,2.0);
                return toByteArray(imageStream);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        //24小时制
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<String>();
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());
            // 用以保存后续开始时间相同的节点
            List<FlowNode> sameStartTimeNodes = new ArrayList<FlowNode>();
            FlowNode sameActivityImpl1 = null;
            // 第一个节点
            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);
            HistoricActivityInstance activityImp2_ ;
            for(int k = i + 1 ; k <= historicActivityInstances.size() - 1; k++) {
                // 后续第1个节点
                activityImp2_ = historicActivityInstances.get(k);

                if ( activityImpl_.getActivityType().equals("userTask") &&
                        activityImp2_.getActivityType().equals("userTask") &&
                        df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))   ) {
                    //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                }
                else {
                    //找到紧跟在后面的一个节点
                    sameActivityImpl1 = (FlowNode)bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());
                    break;
                }

            }
            sameStartTimeNodes.add(sameActivityImpl1); // 将后面第一个节点放在时间相同节点的集合里
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode)bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                }
                else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows() ;

            for (SequenceFlow pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                FlowNode pvmActivityImpl = (FlowNode)bpmnModel.getMainProcess().getFlowElement( pvmTransition.getTargetRef());
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;
    }

    private byte[] toByteArray(InputStream input) {
        ByteArrayOutputStream output = null;
        try{
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }catch (Exception e){
            return output.toByteArray();
        }
        return output.toByteArray();
    }

    /**
     * 直接调用服务生成流程图 只能显示当前流程走向
     *  by liyue
     */
/*    public InputStream ProcessDiagram(){
        ProcessInstance pi =this.runtimeService.createProcessInstanceQuery().processInstanceId(this.processInstanceId).singleResult();
        BpmnModel bpmnModel = this.repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        List<String> activeIds = this.runtimeService.getActiveActivityIds(pi.getId());
        //List<String> highLightedFlows = this.getHighLightedFlows(pi, this.processInstanceId);
        InputStream is = new DefaultProcessDiagramGenerator().generateDiagram(
                bpmnModel, "png",
                activeIds, Collections.<String>emptyList(),
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                null, 1.0);
        return is;
    }*/
}
