package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//工作流流转时，人员添加的意见
@Table(name = "BG_Task_Opinion")
public class TaskOpinion {
    @Id
    private String id;

    private String content;

    private String opinionerId;

    private String opinionerName;

    private String opinionerFullpath;

    private String opinionType;

    private Date opinionTime;

    private String activityId;

    private String resourceId;

    private String executeDetails;

    public String getExecuteDetails() {
        return executeDetails;
    }

    public void setExecuteDetails(String executeDetails) {
        this.executeDetails = executeDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getOpinionerId() {
        return opinionerId;
    }

    public void setOpinionerId(String opinionerId) {
        this.opinionerId = opinionerId == null ? null : opinionerId.trim();
    }

    public String getOpinionerName() {
        return opinionerName;
    }

    public void setOpinionerName(String opinionerName) {
        this.opinionerName = opinionerName == null ? null : opinionerName.trim();
    }

    public String getOpinionerFullpath() {
        return opinionerFullpath;
    }

    public void setOpinionerFullpath(String opinionerFullpath) {
        this.opinionerFullpath = opinionerFullpath == null ? null : opinionerFullpath.trim();
    }

    public String getOpinionType() {
        return opinionType;
    }

    public void setOpinionType(String opinionType) {
        this.opinionType = opinionType == null ? null : opinionType.trim();
    }

    public Date getOpinionTime() {
        return opinionTime;
    }

    public void setOpinionTime(Date opinionTime) {
        this.opinionTime = opinionTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    @Override
    public String toString() {
        return "TaskOpinion{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", opinionerId='" + opinionerId + '\'' +
                ", opinionerName='" + opinionerName + '\'' +
                ", opinionerFullpath='" + opinionerFullpath + '\'' +
                ", opinionType='" + opinionType + '\'' +
                ", opinionTime=" + opinionTime +
                ", activityId='" + activityId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", executeDetails='" + executeDetails + '\'' +
                '}';
    }
}