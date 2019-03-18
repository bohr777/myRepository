package com.owinfo.entity;

/**
 * author: qiyong
 * 2018/8/15 10:37
 * 任务书管理界面返回值
 */
public class TaskBookResult {
    //任务书id
    private String id;

    //系统名称
    private String systemName;

    //承办单位
    private String unitName;

    //业务司局
    private String competentDept;

    //项目名称
    private String projectName;

    //任务书名称
    private String detailsName;

    //下发时间
    private String issueTime;

    //计划完成时间
    private String finishTime;

    //版本号
    private String version;

    //任务书详情状态
    private String tdstatus;

    private String status;

    private String detailId;
    //是否只读
    private String readonly;

    private String isdetail;
    //承办单位全路径
    private String path;

    private String isdetailreadonly;

    public String getIsdetailreadonly() {
        return isdetailreadonly;
    }

    public void setIsdetailreadonly(String isdetailreadonly) {
        this.isdetailreadonly = isdetailreadonly;
    }

    public String getIsdetail() {
        return isdetail;
    }

    public void setIsdetail(String isdetail) {
        this.isdetail = isdetail;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCompetentDept() {
        return competentDept;
    }

    public void setCompetentDept(String competentDept) {
        this.competentDept = competentDept;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTdstatus() {
        return tdstatus;
    }

    public void setTdstatus(String tdstatus) {
        this.tdstatus = tdstatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TaskBookResult{" +
                "id='" + id + '\'' +
                ", systemName='" + systemName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", competentDept='" + competentDept + '\'' +
                ", projectName='" + projectName + '\'' +
                ", detailsName='" + detailsName + '\'' +
                ", issueTime='" + issueTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", version='" + version + '\'' +
                ", tdstatus='" + tdstatus + '\'' +
                ", status='" + status + '\'' +
                ", readonly='" + readonly + '\'' +
                ", readonly='" + isdetail + '\'' +
                ", path='" + path + '\'' +
                ", isdetailreadonly='" + isdetailreadonly + '\'' +
                '}';
    }
}
