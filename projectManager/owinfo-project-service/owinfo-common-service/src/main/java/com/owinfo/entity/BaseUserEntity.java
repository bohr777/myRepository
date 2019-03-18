package com.owinfo.entity;


import javax.persistence.Id;
//海关三统一用户
public class BaseUserEntity
{
    @Id
    private String guid;

    private String deptGuid;

    private String topDeptGuid;

    private String parentTopDeptGuid;

    private String userName;

    private String globalSort;

    private String allPathName;

    private String cardNo;

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getDeptGuid()
    {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid)
    {
        this.deptGuid = deptGuid;
    }

    public String getTopDeptGuid()
    {
        return topDeptGuid;
    }

    public void setTopDeptGuid(String topDeptGuid)
    {
        this.topDeptGuid = topDeptGuid;
    }

    public String getParentTopDeptGuid()
    {
        return parentTopDeptGuid;
    }

    public void setParentTopDeptGuid(String parentTopDeptGuid)
    {
        this.parentTopDeptGuid = parentTopDeptGuid;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getGlobalSort()
    {
        return globalSort;
    }

    public void setGlobalSort(String globalSort)
    {
        this.globalSort = globalSort;
    }

    public String getAllPathName()
    {
        return allPathName;
    }

    public void setAllPathName(String allPathName)
    {
        this.allPathName = allPathName;
    }

    public String getCardNo()
    {
        return cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
}