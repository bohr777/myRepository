package com.owinfo.entity;


import javax.persistence.Id;
import javax.persistence.Table;

//海关三统一部门
@Table(name = "PARAM_CUST")
public class CustEntity {
    @Id
    private String guid;

    private String parentGuid;

    private String custCode;

    private String name;

    private String displayName;

    private String globalSort;

    private String allPathName;

    private Integer levels;

    private String virtualflag;

    private String departmentclasstype;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGlobalSort() {
        return globalSort;
    }

    public void setGlobalSort(String globalSort) {
        this.globalSort = globalSort;
    }

    public String getAllPathName() {
        return allPathName;
    }

    public void setAllPathName(String allPathName) {
        this.allPathName = allPathName;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getVirtualflag() {
        return virtualflag;
    }

    public void setVirtualflag(String virtualflag) {
        this.virtualflag = virtualflag;
    }

    public String getDepartmentclasstype() {
        return departmentclasstype;
    }

    public void setDepartmentclasstype(String departmentclasstype) {
        this.departmentclasstype = departmentclasstype;
    }
}