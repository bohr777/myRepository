package com.owinfo.configurer.Dto.EPT902;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by HHYang on 2017/5/12.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "MESSAGEHEAD")
@XmlType(propOrder = {"MESSAGEID", "MESSAGETYPE","SENDERID","RECEIVERID","SENDTIME"})
public class MESSAGEHEAD {

    private String MESSAGEID;
    private String MESSAGETYPE;
    private String SENDERID;
    private String RECEIVERID;
    private String SENDTIME;

    public String getMESSAGEID() {
        return MESSAGEID;
    }

    public void setMESSAGEID(String MESSAGEID) {
        this.MESSAGEID = MESSAGEID;
    }

    public String getMESSAGETYPE() {
        return MESSAGETYPE;
    }

    public void setMESSAGETYPE(String MESSAGETYPE) {
        this.MESSAGETYPE = MESSAGETYPE;
    }

    public String getSENDERID() {
        return SENDERID;
    }

    public void setSENDERID(String SENDERID) {
        this.SENDERID = SENDERID;
    }

    public String getRECEIVERID() {
        return RECEIVERID;
    }

    public void setRECEIVERID(String RECEIVERID) {
        this.RECEIVERID = RECEIVERID;
    }

    public String getSENDTIME() {
        return SENDTIME;
    }

    public void setSENDTIME(String SENDTIME) {
        this.SENDTIME = SENDTIME;
    }

    public MESSAGEHEAD(String MESSAGEID, String MESSAGETYPE, String SENDERID, String RECEIVERID, String SENDTIME) {
        this.MESSAGEID = MESSAGEID;
        this.MESSAGETYPE = MESSAGETYPE;
        this.SENDERID = SENDERID;
        this.RECEIVERID = RECEIVERID;
        this.SENDTIME = SENDTIME;
    }

    public MESSAGEHEAD() {
    }
}
