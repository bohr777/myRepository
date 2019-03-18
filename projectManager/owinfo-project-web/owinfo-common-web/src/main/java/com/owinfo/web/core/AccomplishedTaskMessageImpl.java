package com.owinfo.web.core;

import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.ArrayOfTaskOperationMessage;
import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.TaskOperation;
import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.TaskOperationMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bruce on 2018/1/9.
 */

//已办
@Component
public class AccomplishedTaskMessageImpl {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccomplishedTaskMessageImpl.class);

    @Autowired
    private BaseUserService baseUserService;

    private Map<Integer, String> map = new HashMap<>();

    public AccomplishedTaskMessageImpl() {
        map.put(2, "Update");
        map.put(4, "Delete");
    }

    public void InsertAccomplishedTasks(JSONArray jsonArray) {
        try {
            JSONArray JSONArray = new JSONArray();
            TaskOperationMessage tasksMessage = new TaskOperationMessage();
            ArrayOfTaskOperationMessage message = new ArrayOfTaskOperationMessage();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                JSONObject jsonobject = new JSONObject();
                if (json.isEmpty() == false) {
//                    jsonobject.put("TaskId", json.getString("taskId") + ":" + i);
                    jsonobject.put("TaskId", json.getString("taskId"));
                    jsonobject.put("SystemCode", json.getString("systemCode"));
                    jsonobject.put("ApplicationName", json.getString("applicationName"));
                    jsonobject.put("UserGuid", json.getString("assignee"));
                    jsonobject.put("Type", "task");
                    jsonobject.put("DeliverTime", sdf.format(new Date()));
                    jsonobject.put("LastModifyTime", sdf.format(new Date()));
                    if (json.has("title") && !"".equals(json.getString("title"))) {
                        jsonobject.put("Title", json.getString("title"));
                    }
                    //                    flag 1是待办    2是已办
                    jsonobject.put("Url", json.getString("url") + "?taskbookId=" + json.getString("taskbookId") +
                                    "&node=" + URLEncoder.encode(json.getString("taskName"), "utf-8") +
                                    "&taskId=" + json.getString("taskId") + "&flag=2" +
                                    "&processInstanceId=" + json.getString("processInstanceId") +
                                    "&title=" + URLEncoder.encode(json.getString("title"), "utf-8") +
                                    "&executionId=" + json.getString("executionId") +
                                    "&userId=" + json.getString("assignee")
//                            +"&userName=" + URLEncoder.encode(json.getString("userName"), "utf-8") +
//                            "&userPath=" + URLEncoder.encode(json.getString("userPath"), "utf-8")
                    );
                    jsonobject.put("Emergency", "0");
                    jsonobject.put("SourceName", json.getString("userName"));
//                    !!!!!!!!!!!!!!!!!!!!!!!!!!!

                    jsonobject.put("Purpose", json.getString("purpose"));
                    jsonobject.put("TaskLevel", 2);
                    jsonobject.put("ResourceId", json.getString("resourceId"));
                    jsonobject.put("Status", "1");
                    jsonobject.put("Opened", true);
                    if (json.has("createTime") && null != json.get("createTime")
                            && !"".equals(json.get("createTime"))) {
                        jsonobject.put("TaskStartTime", sdf.format(new Date(json.getLong("createTime"))));
                    }
//                if (json.has("endTime") && null != json.get("endTime")
//                        && !"".equals(json.get("endTime"))) {
//                    jsonobject.put("ExpireTime", json.get("endTime").toString());
//                }

                    String uuid = json.getString("assignee");
                    JSONObject aaa = JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}");
                    JSONObject user = baseUserService.selectUserById(aaa);

                    jsonobject.put("SendToName", user.getString("userName"));
                    jsonobject.put("SourceId", json.getString("userId"));
//                if (json.has("ReadTime") && null != json.getString("ReadTime")
//                        && !"".equals(json.getString("ReadTime"))) {
//                    jsonobject.put("ReadTime", json.getString("ReadTime"));
//                }
                    jsonobject.put("CompleteTime", sdf.format(new Date(json.getLong("endTime"))));
                    jsonobject.put("ProcessId", json.getString("processInstanceId"));
                    jsonobject.put("ActivityId", json.getString("activityId"));
                    jsonobject.put("Operation", json.getInt("Operation"));
                    System.out.println("已办   "+jsonobject.toString());
                    logger.info("已办   "+jsonobject.toString());
                }
                JSONArray.add(jsonobject);
            }
            int operation = (int) jsonArray.getJSONObject(0).get("Operation");
            TaskOperation taskOperation = TaskOperation.fromValue(map.get(operation));
            tasksMessage.setOperation(taskOperation);
            message.getTaskOperationMessage().add(tasksMessage);
            if (map.get(operation).equals("Update")) {
//                insertAccomplished.Update(JSONArray.toString(), message);
                InsertAccomplished insertAccomplished = new InsertAccomplished();
                insertAccomplished.setMessage(message);
                insertAccomplished.setJSONArray(JSONArray.toString());
                insertAccomplished.setType(2);
                Thread thread = new Thread(insertAccomplished);
                thread.start();
            }
            if (map.get(operation).equals("Delete")) {
//                insertAccomplished.Delete(JSONArray.toString(), message);
                InsertAccomplished insertAccomplished = new InsertAccomplished();
                insertAccomplished.setMessage(message);
                insertAccomplished.setJSONArray(JSONArray.toString());
                insertAccomplished.setType(4);
                Thread thread = new Thread(insertAccomplished);
                thread.start();
//                System.out.println("InsertAccomplishedTasks   delete  "+thread.getState());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
