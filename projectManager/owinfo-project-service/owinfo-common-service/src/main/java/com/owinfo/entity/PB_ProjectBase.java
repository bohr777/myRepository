package com.owinfo.entity;

import javax.persistence.Id;


//海关三统一项目
public class PB_ProjectBase {
    @Id
    private String subject;
    private String organizer_guid;
    private String organizer_name;
    private String contract_peperson;
    private String organizer_fullpath;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrganizer_guid() {
        return organizer_guid;
    }

    public void setOrganizer_guid(String organizer_guid) {
        this.organizer_guid = organizer_guid;
    }

    public String getOrganizer_name() {
        return organizer_name;
    }

    public void setOrganizer_name(String organizer_name) {
        this.organizer_name = organizer_name;
    }

    public String getContract_peperson() {
        return contract_peperson;
    }

    public void setContract_peperson(String contract_peperson) {
        this.contract_peperson = contract_peperson;
    }

    public String getOrganizer_fullpath() {
        return organizer_fullpath;
    }

    public void setOrganizer_fullpath(String organizer_fullpath) {
        this.organizer_fullpath = organizer_fullpath;
    }

}
