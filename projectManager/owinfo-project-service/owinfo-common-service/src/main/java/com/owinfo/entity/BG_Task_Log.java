package com.owinfo.entity;

import javax.persistence.Id;
import java.util.Date;

//任务书日志
public class BG_Task_Log {
    @Id
    private String guid;

    private String taskid;

    private String content;

    private Date logDate;

    private Date createTime;

    private String contentpertcent;

    private String userid;

    private String username;

    private String userfullpath;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContentpertcent() {
        return contentpertcent;
    }

    public void setContentpertcent(String contentpertcent) {
        this.contentpertcent = contentpertcent;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserfullpath() {
        return userfullpath;
    }

    public void setUserfullpath(String userfullpath) {
        this.userfullpath = userfullpath;
    }
}