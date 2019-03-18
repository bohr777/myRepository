package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//任务书
@Table(name = "BG_TaskBook")
public class TaskBook {
    @Id
    private String id;

    private String origin;

    private String mailNumber;

    private String mailTitle;

    private Date mailTime;

    private String competentDept;

    private Date createTime;

    private String founderId;

    private String founderName;

    private String issueId;

    private String issueName;

    private String finalModifierId;

    private String finalModifierName;

    private Date finalModify;

    private String founderFullpath;

    private String finalModifierFullpath;

    private String projectName;

    private String tag;

    private String importance;

    private Date issueTime;

    private String status;

    private String isDelete;

    private Date planTime;

    private Date finishTime;

    private String readonly;

    private String isdetail;

    public String getIsdetail() {
        return isdetail;
    }

    public void setIsdetail(String isdetail) {
        this.isdetail = isdetail;
    }

    public void setIssueId(String issueId){
        this.issueId=issueId;
    }
    public String getIssueId(){return issueId;}
    public void setIssueName(String issueName){
        this.issueName=issueName;
    }
    public String getIssueName(){return issueName;}
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getMailNumber() {
        return mailNumber;
    }

    public void setMailNumber(String mailNumber) {
        this.mailNumber = mailNumber == null ? null : mailNumber.trim();
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle == null ? null : mailTitle.trim();
    }

    public Date getMailTime() {
        return mailTime;
    }

    public void setMailTime(Date mailTime) {
        this.mailTime = mailTime;
    }

    public String getCompetentDept() {
        return competentDept;
    }

    public void setCompetentDept(String competentDept) {
        this.competentDept = competentDept == null ? null : competentDept.trim();
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

    public Date getFinalModify() {
        return finalModify;
    }

    public void setFinalModify(Date finalModify) {
        this.finalModify = finalModify;
    }

    public String getFounderFullpath() {
        return founderFullpath;
    }

    public void setFounderFullpath(String founderFullpath) {
        this.founderFullpath = founderFullpath == null ? null : founderFullpath.trim();
    }

    public String getFinalModifierFullpath() {
        return finalModifierFullpath;
    }

    public void setFinalModifierFullpath(String finalModifierFullpath) {
        this.finalModifierFullpath = finalModifierFullpath == null ? null : finalModifierFullpath.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance == null ? null : importance.trim();
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    @Override
    public String toString() {
        return "TaskBook{" +
                "id='" + id + '\'' +
                ", origin='" + origin + '\'' +
                ", mailNumber='" + mailNumber + '\'' +
                ", mailTitle='" + mailTitle + '\'' +
                ", mailTime=" + mailTime +
                ", competentDept='" + competentDept + '\'' +
                ", createTime=" + createTime +
                ", founderId='" + founderId + '\'' +
                ", founderName='" + founderName + '\'' +
                ", issueId='" + issueId + '\'' +
                ", issueName='" + issueName + '\'' +
                ", finalModifierId='" + finalModifierId + '\'' +
                ", finalModifierName='" + finalModifierName + '\'' +
                ", finalModify=" + finalModify +
                ", founderFullpath='" + founderFullpath + '\'' +
                ", finalModifierFullpath='" + finalModifierFullpath + '\'' +
                ", projectName='" + projectName + '\'' +
                ", tag='" + tag + '\'' +
                ", importance='" + importance + '\'' +
                ", issueTime=" + issueTime +
                ", status='" + status + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", planTime=" + planTime +
                ", finishTime=" + finishTime +
                ", readonly='" + readonly + '\'' +
                ", isdetail='" + isdetail + '\'' +
                '}';
    }
}