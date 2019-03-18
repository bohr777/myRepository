package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "STM_SystemInfo")
public class STMSystemInfo {
    @Id
    private String id;

    private String sysCode;

    private String parentid;

    private Integer sysTier;

    private String sysName;

    private String sysLevel;

    private String sysCategory;

    private Date onlineDate;

    private Date outlineDate;

    private String url;

    private String introduction;

    private Integer openMode;

    private String creator;

    private String creatorName;

    private String creatorPath;

    private Date createTime;

    private String modifor;

    private String modiforName;

    private String modiforPath;

    private Date modiforTime;

    private String l1SysId;

    private String l1SysName;

    private String l2SysId;

    private String l2SysName;

    private String l3SysId;

    private String l3SysName;

    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public Integer getSysTier() {
        return sysTier;
    }

    public void setSysTier(Integer sysTier) {
        this.sysTier = sysTier;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(String sysLevel) {
        this.sysLevel = sysLevel == null ? null : sysLevel.trim();
    }

    public String getSysCategory() {
        return sysCategory;
    }

    public void setSysCategory(String sysCategory) {
        this.sysCategory = sysCategory == null ? null : sysCategory.trim();
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Date getOutlineDate() {
        return outlineDate;
    }

    public void setOutlineDate(Date outlineDate) {
        this.outlineDate = outlineDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Integer openMode) {
        this.openMode = openMode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public String getCreatorPath() {
        return creatorPath;
    }

    public void setCreatorPath(String creatorPath) {
        this.creatorPath = creatorPath == null ? null : creatorPath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifor() {
        return modifor;
    }

    public void setModifor(String modifor) {
        this.modifor = modifor == null ? null : modifor.trim();
    }

    public String getModiforName() {
        return modiforName;
    }

    public void setModiforName(String modiforName) {
        this.modiforName = modiforName == null ? null : modiforName.trim();
    }

    public String getModiforPath() {
        return modiforPath;
    }

    public void setModiforPath(String modiforPath) {
        this.modiforPath = modiforPath == null ? null : modiforPath.trim();
    }

    public Date getModiforTime() {
        return modiforTime;
    }

    public void setModiforTime(Date modiforTime) {
        this.modiforTime = modiforTime;
    }

    public String getL1SysId() {
        return l1SysId;
    }

    public void setL1SysId(String l1SysId) {
        this.l1SysId = l1SysId == null ? null : l1SysId.trim();
    }

    public String getL1SysName() {
        return l1SysName;
    }

    public void setL1SysName(String l1SysName) {
        this.l1SysName = l1SysName == null ? null : l1SysName.trim();
    }

    public String getL2SysId() {
        return l2SysId;
    }

    public void setL2SysId(String l2SysId) {
        this.l2SysId = l2SysId == null ? null : l2SysId.trim();
    }

    public String getL2SysName() {
        return l2SysName;
    }

    public void setL2SysName(String l2SysName) {
        this.l2SysName = l2SysName == null ? null : l2SysName.trim();
    }

    public String getL3SysId() {
        return l3SysId;
    }

    public void setL3SysId(String l3SysId) {
        this.l3SysId = l3SysId == null ? null : l3SysId.trim();
    }

    public String getL3SysName() {
        return l3SysName;
    }

    public void setL3SysName(String l3SysName) {
        this.l3SysName = l3SysName == null ? null : l3SysName.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}