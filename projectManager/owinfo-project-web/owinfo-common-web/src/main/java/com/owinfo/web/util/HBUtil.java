package com.owinfo.web.util;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.AccomplishedTaskMessageImpl;
import com.owinfo.web.core.TaskMessageImpl;
import com.owinfo.web.service.ActivitiService;
import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.service.TaskInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */
@Component
public class HBUtil {
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private AccomplishedTaskMessageImpl accomplishedTaskMessage;
    @Value("${fontEnd}")
    private String url;
    @Value("${applicationName}")
    private String applicationName;
    @Value("${systemCode}")
    private String systemCode;
    @Autowired
    private TaskMessageImpl taskMessage;

    //添加待办
    public void updateRuntime(JSONObject param) {
        try{
            JSONArray runtime = new JSONArray();
//            if(activitiService.runtimeTasks(param).has("list")){
                runtime = activitiService.runtimeTasks(param).getJSONArray("list");
//            }
            runtime=compile(runtime,2);
            for(int i=0;i<runtime.size();i++){
//                String uuid = runtime.getJSONObject(i).getString("assignee");
//                runtime.getJSONObject(i).put("userId",uuid);
//                JSONObject aaa = JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}");
//                JSONObject user = baseUserService.selectUserById(aaa);
//                runtime.getJSONObject(i).put("userName",user.getString("userName"));
//                runtime.getJSONObject(i).put("userPath",user.getString("userPath"));

                runtime.getJSONObject(i).put("userId",param.getString("userId"));
                runtime.getJSONObject(i).put("userName",param.getString("userName"));
                runtime.getJSONObject(i).put("userPath",param.getString("userPath"));
            }
//            TaskMessageImpl taskMessage=new TaskMessageImpl();
            taskMessage.MixOperateTasksOfJson(runtime);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
    //删除待办添加已办
    public void deleteRuntimeInsertHistoric(JSONObject param) {
        try{
            JSONArray runtime = null;
            if(null==(runtime=activitiService.historicTasks(param).getJSONArray("list"))||runtime.size()<1){
                return;
            }
            runtime=compile(runtime,4);
            for(int i=0;i<runtime.size();i++){
//                String uuid = runtime.getJSONObject(i).getString("assignee");
//                runtime.getJSONObject(i).put("userId",uuid);
//                JSONObject aaa = JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}");
//                JSONObject user = baseUserService.selectUserById(aaa);
//                runtime.getJSONObject(i).put("userName",user.getString("userName"));
//                runtime.getJSONObject(i).put("userPath",user.getString("userPath"));

                runtime.getJSONObject(i).put("userId",param.getString("userId"));
                runtime.getJSONObject(i).put("userName",param.getString("userName"));
                runtime.getJSONObject(i).put("userPath",param.getString("userPath"));
            }
//            TaskMessageImpl taskMessage=new TaskMessageImpl();
            taskMessage.MixOperateTasksOfJson(runtime);
            for(int i=0;i<runtime.size();i++){
                runtime.getJSONObject(i).put("Operation",2);
                runtime.getJSONObject(i).put("endTime",new Date().getTime());
            }
//            =new AccomplishedTaskMessageImpl();
            accomplishedTaskMessage.InsertAccomplishedTasks(runtime);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
    public void deleteHistoric(JSONObject param) {
        try{
            JSONArray historic = activitiService.historicTasks(param).getJSONArray("list");
            historic=compile(historic,4);
            AccomplishedTaskMessageImpl accomplishedTaskMessage=new AccomplishedTaskMessageImpl();
            accomplishedTaskMessage.InsertAccomplishedTasks(historic);
        }catch (Exception e){
            return;
        }
    }

    public JSONArray compile(JSONArray params,int Operation){
        List<JSONObject> m=new ArrayList();
        for(int i=params.size()-1;i>=0;i--){
            JSONObject temp=params.getJSONObject(i);
            temp.put("Operation",Operation);
            if(temp.getString("assignee").indexOf(",")!=-1){
                String[] assignees=temp.getString("assignee").split(",");
                for(int j=0;j<assignees.length;j++){
                    JSONObject t=new JSONObject();
                    t.putAll(temp);
                    t.put("assignee",assignees[j]);
                    JSONObject param = new JSONObject();
                    param.put("activityId", t.getString("taskId"));
                    JSONObject taskInfo = taskInfoService.getById(param);
                    t.putAll(taskInfo);
                    t.put("url",url);
                    t.put("applicationName",applicationName);
                    t.put("systemCode",systemCode);
                    params.add(t);
                }
                params.remove(i);
            }else{
                JSONObject param = new JSONObject();
                param.put("activityId", temp.getString("taskId"));
                JSONObject taskInfo = taskInfoService.getById(param);
                temp.putAll(taskInfo);
                temp.put("url",url);
                temp.put("applicationName",applicationName);
                temp.put("systemCode",systemCode);
            }
        }
        return params;
    }
}
