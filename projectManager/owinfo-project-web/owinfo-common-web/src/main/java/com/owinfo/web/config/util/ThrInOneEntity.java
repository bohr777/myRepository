package com.owinfo.web.config.util;

/**
 * Created by Administrator on 2017/7/21.
 */
public class ThrInOneEntity
{
    private String uuid;
    private String userName;
    private String loginName;
    private String allPathName;
    private String role;
    private boolean isAdmin;

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAllPathName(){return  allPathName;}

    public void setAllPathName(String allPathName){this.allPathName=allPathName;}

    public String getRole(){return role;}

    public void setRole(String role){this.role=role;}
}
