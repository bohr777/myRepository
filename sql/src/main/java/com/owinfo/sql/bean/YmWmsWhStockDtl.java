package com.owinfo.sql.bean;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class YmWmsWhStockDtl {
    @Id
    @GeneratedValue
    private Long rid;

    private String dn;

    private String warehousecode;

    private String isQualit;

    private String stockpositioncode;

    private String gName;

    private String gModel;

    private String sn;

    private Integer gQty;

    private Integer gUnit;

    private String companyNo;

    private String pn;

    private Date productDate;

    private Date invalidDate;

    private Integer safeDate;

    private Date gmtCreate;

    private Date gmtModified;

    private String extend1;

    private String extend2;

    private String extend3;

    private String extend4;

    private String extend5;

    private Integer categoryId;

    private String purchaseNo;

    private String dnExternal;

    private String isUsable;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn == null ? null : dn.trim();
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getIsQualit() {
        return isQualit;
    }

    public void setIsQualit(String isQualit) {
        this.isQualit = isQualit == null ? null : isQualit.trim();
    }

    public String getStockpositioncode() {
        return stockpositioncode;
    }

    public void setStockpositioncode(String stockpositioncode) {
        this.stockpositioncode = stockpositioncode == null ? null : stockpositioncode.trim();
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName == null ? null : gName.trim();
    }

    public String getgModel() {
        return gModel;
    }

    public void setgModel(String gModel) {
        this.gModel = gModel == null ? null : gModel.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getgQty() {
        return gQty;
    }

    public void setgQty(Integer gQty) {
        this.gQty = gQty;
    }

    public Integer getgUnit() {
        return gUnit;
    }

    public void setgUnit(Integer gUnit) {
        this.gUnit = gUnit;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo == null ? null : companyNo.trim();
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn == null ? null : pn.trim();
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Integer getSafeDate() {
        return safeDate;
    }

    public void setSafeDate(Integer safeDate) {
        this.safeDate = safeDate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3 == null ? null : extend3.trim();
    }

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4 == null ? null : extend4.trim();
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5 == null ? null : extend5.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo == null ? null : purchaseNo.trim();
    }

    public String getDnExternal() {
        return dnExternal;
    }

    public void setDnExternal(String dnExternal) {
        this.dnExternal = dnExternal == null ? null : dnExternal.trim();
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable == null ? null : isUsable.trim();
    }

    @Override
    public String toString() {
        return "YmWmsWhStockDtl{" +
                "rid=" + rid +
                ", dn='" + dn + '\'' +
                ", warehousecode='" + warehousecode + '\'' +
                ", isQualit='" + isQualit + '\'' +
                ", stockpositioncode='" + stockpositioncode + '\'' +
                ", gName='" + gName + '\'' +
                ", gModel='" + gModel + '\'' +
                ", sn='" + sn + '\'' +
                ", gQty=" + gQty +
                ", gUnit=" + gUnit +
                ", companyNo='" + companyNo + '\'' +
                ", pn='" + pn + '\'' +
                ", productDate=" + productDate +
                ", invalidDate=" + invalidDate +
                ", safeDate=" + safeDate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", extend4='" + extend4 + '\'' +
                ", extend5='" + extend5 + '\'' +
                ", categoryId=" + categoryId +
                ", purchaseNo='" + purchaseNo + '\'' +
                ", dnExternal='" + dnExternal + '\'' +
                ", isUsable='" + isUsable + '\'' +
                '}';
    }
}