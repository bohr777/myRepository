package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "STM_Ogus")
public class STMOgus {
    @Id
    private String id;

    private String sysId;

    private String ogusType;

    private String ogusId;

    private String ogusName;

    private String ogusPath;

    private String parentDeptId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId == null ? null : sysId.trim();
    }

    public String getOgusType() {
        return ogusType;
    }

    public void setOgusType(String ogusType) {
        this.ogusType = ogusType == null ? null : ogusType.trim();
    }

    public String getOgusId() {
        return ogusId;
    }

    public void setOgusId(String ogusId) {
        this.ogusId = ogusId == null ? null : ogusId.trim();
    }

    public String getOgusName() {
        return ogusName;
    }

    public void setOgusName(String ogusName) {
        this.ogusName = ogusName == null ? null : ogusName.trim();
    }

    public String getOgusPath() {
        return ogusPath;
    }

    public void setOgusPath(String ogusPath) {
        this.ogusPath = ogusPath == null ? null : ogusPath.trim();
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId == null ? null : parentDeptId.trim();
    }
}