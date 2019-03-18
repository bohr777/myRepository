package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//任务书细分
@Table(name = "BG_TaskBook_Details")
public class Details {
    @Id
    private String id;

    private String systemName;

    private String serialNumber;

    private String detailsNumber;

    private String detailsName;

    private Date planTime;

    private String importance;

    private String status;

    private String version;

    private String unitName;

    private Date createTime;

    private String founderId;

    private String founderName;

    private String founderFullpath;

    private Date finalModify;

    private String finalModifierId;

    private String finalModifierName;

    private String finalModifierFullpath;

    private String isDelete;

    private String taskbookId;

    private String systemId;

    private Date finishTime;

    private int frequency;

    public void setFrequency(int frequency){
        this.frequency=frequency;
    }
    public int getFrequency(){
        return frequency;
    }

    public Date getFinishTime(){
        return finishTime;
    }

    public void setFinishTime(Date finishTime){
        this.finishTime=finishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getDetailsNumber() {
        return detailsNumber;
    }

    public void setDetailsNumber(String detailsNumber) {
        this.detailsNumber = detailsNumber == null ? null : detailsNumber.trim();
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName == null ? null : detailsName.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance == null ? null : importance.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFounderId() {
        return founderId;
    }

    public void setFounderId(String founderId) {
        this.founderId = founderId == null ? null : founderId.trim();
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName == null ? null : founderName.trim();
    }

    public String getFounderFullpath() {
        return founderFullpath;
    }

    public void setFounderFullpath(String founderFullpath) {
        this.founderFullpath = founderFullpath == null ? null : founderFullpath.trim();
    }

    public Date getFinalModify() {
        return finalModify;
    }

    public void setFinalModify(Date finalModify) {
        this.finalModify = finalModify;
    }

    public String getFinalModifierId() {
        return finalModifierId;
    }

    public void setFinalModifierId(String finalModifierId) {
        this.finalModifierId = finalModifierId == null ? null : finalModifierId.trim();
    }

    public String getFinalModifierName() {
        return finalModifierName;
    }

    public void setFinalModifierName(String finalModifierName) {
        this.finalModifierName = finalModifierName == null ? null : finalModifierName.trim();
    }

    public String getFinalModifierFullpath() {
        return finalModifierFullpath;
    }

    public void setFinalModifierFullpath(String finalModifierFullpath) {
        this.finalModifierFullpath = finalModifierFullpath == null ? null : finalModifierFullpath.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getTaskbookId() {
        return taskbookId;
    }

    public void setTaskbookId(String taskbookId) {
        this.taskbookId = taskbookId == null ? null : taskbookId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", systemName='" + systemName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", detailsNumber='" + detailsNumber + '\'' +
                ", detailsName='" + detailsName + '\'' +
                ", planTime=" + planTime +
                ", importance='" + importance + '\'' +
                ", status='" + status + '\'' +
                ", version='" + version + '\'' +
                ", unitName='" + unitName + '\'' +
                ", createTime=" + createTime +
                ", founderId='" + founderId + '\'' +
                ", founderName='" + founderName + '\'' +
                ", founderFullpath='" + founderFullpath + '\'' +
                ", finalModify=" + finalModify +
                ", finalModifierId='" + finalModifierId + '\'' +
                ", finalModifierName='" + finalModifierName + '\'' +
                ", finalModifierFullpath='" + finalModifierFullpath + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", taskbookId='" + taskbookId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", finishTime=" + finishTime +
                ", frequency=" + frequency +
                '}';
    }
}