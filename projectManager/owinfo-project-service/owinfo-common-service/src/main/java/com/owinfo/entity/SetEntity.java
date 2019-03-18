package com.owinfo.entity;


import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//人员设置
@Table(name = "BG_Setup")
public class SetEntity {
    @Id
    private String userId;

    private String shu;

    private String guan;

    private Date createTime;

    private String founderId;

    private String founderName;

    private String founderFullpath;

    private Date finalModify;

    private String finalModifierId;

    private String finalModifierName;

    private String finalModifierFullpath;

    private String unitId;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShu() {
        return shu;
    }

    public void setShu(String shu) {
        this.shu = shu;
    }

    public String getGuan() {
        return guan;
    }

    public void setGuan(String guan) {
        this.guan = guan;
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
        this.founderId = founderId;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public String getFounderFullpath() {
        return founderFullpath;
    }

    public void setFounderFullpath(String founderFullpath) {
        this.founderFullpath = founderFullpath;
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
        this.finalModifierId = finalModifierId;
    }

    public String getFinalModifierName() {
        return finalModifierName;
    }

    public void setFinalModifierName(String finalModifierName) {
        this.finalModifierName = finalModifierName;
    }

    public String getFinalModifierFullpath() {
        return finalModifierFullpath;
    }

    public void setFinalModifierFullpath(String finalModifierFullpath) {
        this.finalModifierFullpath = finalModifierFullpath;
    }
}