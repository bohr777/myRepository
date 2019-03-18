package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//任务书系统
@Table(name = "BG_System")
public class Systems {
    @Id
    private String id;

    private Date createTime;

    private String founderId;

    private String founderName;

    private String founderFullpath;

    private String finalModifierId;

    private String finalModifierName;

    private String finalModifierFullpath;

    private Date finalModify;

    private String organizerId;

    private String unitName;

    private String isDelete;

    private String systemName;

    private String unitId;

    private String unitFullpath;

    private String taskbookId;

    private String organizerName;

    private String organizerFullpath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId == null ? null : organizerId.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getUnitFullpath() {
        return unitFullpath;
    }

    public void setUnitFullpath(String unitFullpath) {
        this.unitFullpath = unitFullpath == null ? null : unitFullpath.trim();
    }

    public String getTaskbookId() {
        return taskbookId;
    }

    public void setTaskbookId(String taskbookId) {
        this.taskbookId = taskbookId == null ? null : taskbookId.trim();
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
}