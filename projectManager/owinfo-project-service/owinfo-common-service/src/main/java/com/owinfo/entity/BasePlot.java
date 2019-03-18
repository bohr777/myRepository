package com.owinfo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/12.
 */
public class BasePlot {
    private String status1;
    private String status2;
    private String createTime;
    public void setStatus1(String status1){
        this.status1=status1;
    }
    public String getStatus1(){
        return status1;
    }
    public void setStatus2(String status2){
        this.status2=status2;
    }
    public String getStatus2(){
        return status2;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return createTime;
    }
}
