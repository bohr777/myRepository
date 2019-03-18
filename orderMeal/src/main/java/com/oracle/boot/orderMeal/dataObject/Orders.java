package com.oracle.boot.orderMeal.dataObject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Orders {
	
	@Id
	@GeneratedValue
    private Integer oid;

    private Integer onum;

    private Integer uid;

    private Double oamount;

    private Integer ostatus;

    private Integer paystatus;

    private Integer issend;

    private Integer isdel;

    private Date ocreatetime;

    private Date oupdatetime;
    
    private String productsdetail;
    
    private String remark;
    
//分页  ----------------------
    
    private Integer size;   //每页显示的条数
    
    private Integer currentPage;   //从第几条数据开始的

    public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	//分页  ----------------------
    
    public String getProductsdetail() {
		return productsdetail;
	}

	public void setProductsdetail(String productsdetail) {
		this.productsdetail = productsdetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private Integer sid;

	private String uname;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getOnum() {
		return onum;
	}

	public void setOnum(Integer onum) {
		this.onum = onum;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Double getOamount() {
		return oamount;
	}

	public void setOamount(Double oamount) {
		this.oamount = oamount;
	}

	public Integer getOstatus() {
		return ostatus;
	}

	public void setOstatus(Integer ostatus) {
		this.ostatus = ostatus;
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}

	public Integer getIssend() {
		return issend;
	}

	public void setIssend(Integer issend) {
		this.issend = issend;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Date getOcreatetime() {
		return ocreatetime;
	}

	public void setOcreatetime(Date ocreatetime) {
		this.ocreatetime = ocreatetime;
	}

	public Date getOupdatetime() {
		return oupdatetime;
	}

	public void setOupdatetime(Date oupdatetime) {
		this.oupdatetime = oupdatetime;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
    
   

}