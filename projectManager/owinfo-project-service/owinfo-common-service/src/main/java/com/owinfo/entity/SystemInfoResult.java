package com.owinfo.entity;

/**
 * author: qiyong
 * 2018/8/21 9:25
 */
public class SystemInfoResult {

    private String id ;
    private String systemName;
    private String stmOgusId;
    private String ogusType;
    private String ogusName;
    private String ogusPath;
    private String parentDeptID;
    //承办单位
    private String organizerName;
    //负责人
    private String organizerPerson;

    private String managerName;

    private String organizerPath;
    //系统层级
    private String systemTier;

    public String getOrganizerPath() {
        return organizerPath;
    }

    public void setOrganizerPath(String organizerPath) {
        this.organizerPath = organizerPath;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getStmOgusId() {
        return stmOgusId;
    }

    public void setStmOgusId(String stmOgusId) {
        this.stmOgusId = stmOgusId;
    }

    public String getOgusType() {
        return ogusType;
    }

    public void setOgusType(String ogusType) {
        this.ogusType = ogusType;
    }

    public String getOgusName() {
        return ogusName;
    }

    public void setOgusName(String ogusName) {
        this.ogusName = ogusName;
    }

    public String getOgusPath() {
        return ogusPath;
    }

    public void setOgusPath(String ogusPath) {
        this.ogusPath = ogusPath;
    }

    public String getParentDeptID() {
        return parentDeptID;
    }

    public void setParentDeptID(String parentDeptID) {
        this.parentDeptID = parentDeptID;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerPerson() {
        return organizerPerson;
    }

    public void setOrganizerPerson(String organizerPerson) {
        this.organizerPerson = organizerPerson;
    }

    public String getSystemTier() {
        return systemTier;
    }

    public void setSystemTier(String systemTier) {
        this.systemTier = systemTier;
    }

    @Override
    public String toString() {
        return "SystemInfoResult{" +
                "id='" + id + '\'' +
                ", systemName='" + systemName + '\'' +
                ", stmOgusId='" + stmOgusId + '\'' +
                ", ogusType='" + ogusType + '\'' +
                ", ogusName='" + ogusName + '\'' +
                ", ogusPath='" + ogusPath + '\'' +
                ", parentDeptID='" + parentDeptID + '\'' +
                ", organizerName='" + organizerName + '\'' +
                ", organizerPerson='" + organizerPerson + '\'' +
                ", managerName='" + managerName + '\'' +
                ", organizerPath='" + organizerPath + '\'' +
                ", systemTier='" + systemTier + '\'' +
                '}';
    }
}
