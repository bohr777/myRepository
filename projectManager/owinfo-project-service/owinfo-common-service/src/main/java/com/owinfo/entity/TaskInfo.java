package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

//工作流与任务书对应关系
@Table(name = "BG_Task_Info")
public class TaskInfo {
    @Id
    private String activityId;

    private String resourceId;

    private String title;

    private String purpose;

    private String prevAssignee;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getPrevAssignee() {
        return prevAssignee;
    }

    public void setPrevAssignee(String prevAssignee) {
        this.prevAssignee = prevAssignee;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "activityId='" + activityId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", title='" + title + '\'' +
                ", purpose='" + purpose + '\'' +
                ", prevAssignee='" + prevAssignee + '\'' +
                '}';
    }
}