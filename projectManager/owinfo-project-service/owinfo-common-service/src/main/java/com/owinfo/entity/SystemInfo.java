package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//任务书系统维护
@Table(name = "BG_System_Info")
public class SystemInfo {
    @Id
    private String id;

    private String systemName;

    private String organizerId;

    private String organizerName;

    private String organizerFullpath;

    private String deptId;

    private String deptName;

    private String deptFullpath;

    private String network;

    private String founderId;

    private String founderName;

    private String founderFullpath;

    private Date createTime;

    private String finalModifierId;

    private String finalModifierName;

    private String finalModifierFullpath;

    private String managerId;

    private String managerName;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerFullpath() {
        return managerFullpath;
    }

    public void setManagerFullpath(String managerFullpath) {
        this.managerFullpath = managerFullpath;
    }

    private String managerFullpath;

    private Date finalModify;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    private String isDelete;

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

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId == null ? null : organizerId.trim();
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName == null ? null : organizerName.trim();
    }

    public String getOrganizerFullpath() {
        return organizerFullpath;
    }

    public void setOrganizerFullpath(String organizerFullpath) {
        this.organizerFullpath = organizerFullpath == null ? null : organizerFullpath.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptFullpath() {
        return deptFullpath;
    }

    public void setDeptFullpath(String deptFullpath) {
        this.deptFullpath = deptFullpath == null ? null : deptFullpath.trim();
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network == null ? null : network.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getFinalModify() {
        return finalModify;
    }

    public void setFinalModify(Date finalModify) {
        this.finalModify = finalModify;
    }
}