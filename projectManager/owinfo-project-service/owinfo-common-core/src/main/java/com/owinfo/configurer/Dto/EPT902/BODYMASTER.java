package com.owinfo.configurer.Dto.EPT902;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Created by HHYang on 2017/5/12.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "BODYMASTER")
@XmlType(propOrder = {"flightNumber", "fax","email","carrier","telephone","departureAirport","arrivialAirport","sta","std"})
public class BODYMASTER  {
    private String id;

    @Column(name = "flight_number")
    private String flightNumber;

    private String fax;

    private String email;

    private String carrier;

    private String telephone;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "arrivial_airport")
    private String arrivialAirport;

    /**
     * STD
     */
    private Date std;

    private Date sta;


    /**
     * ״̬
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivialAirport() {
        return arrivialAirport;
    }

    public void setArrivialAirport(String arrivialAirport) {
        this.arrivialAirport = arrivialAirport;
    }

    public Date getStd() {
        return std;
    }

    public void setStd(Date std) {
        this.std = std;
    }

    public Date getSta() {
        return sta;
    }

    public void setSta(Date sta) {
        this.sta = sta;
    }

    public void setSta(String 出发时间) {
    }

    public void setStd(String 到达时间) {
    }


//    public BODYMASTER(String id, String flightNumber, String head_MESSAGETYPE, String head_SENDERID, String head_RECEIVERID, String head_SENDTIME, String MESSAGEID, String MESSAGETYPE, String CHKFLAG, String CHKMARK, String NOTICEDATE, String CBECBILLNO, Date create_time, Date modified_time) {
//        this.head_MESSAGEID = head_MESSAGEID;
//        this.head_MESSAGETYPE = head_MESSAGETYPE;
//        this.head_SENDERID = head_SENDERID;
//        this.head_RECEIVERID = head_RECEIVERID;
//        this.head_SENDTIME = head_SENDTIME;
//        this.MESSAGEID = MESSAGEID;
//        this.MESSAGETYPE = MESSAGETYPE;
//        this.CHKFLAG = CHKFLAG;
//        this.CHKMARK = CHKMARK;
//        this.NOTICEDATE = NOTICEDATE;
//        this.CBECBILLNO = CBECBILLNO;
//        this.create_time = create_time;
//        this.modified_time = modified_time;
//    }
//
//    public BODYMASTER() {
//    }
//
//    @Override
//    public String toString() {
//        return "BODYMASTER{" +
//                "id=" + id +
//                ", head_MESSAGEID='" + head_MESSAGEID + '\'' +
//                ", head_MESSAGETYPE='" + head_MESSAGETYPE + '\'' +
//                ", head_SENDERID='" + head_SENDERID + '\'' +
//                ", head_RECEIVERID='" + head_RECEIVERID + '\'' +
//                ", head_SENDTIME='" + head_SENDTIME + '\'' +
//                ", MESSAGEID='" + MESSAGEID + '\'' +
//                ", MESSAGETYPE='" + MESSAGETYPE + '\'' +
//                ", CHKFLAG='" + CHKFLAG + '\'' +
//                ", CHKMARK='" + CHKMARK + '\'' +
//                ", NOTICEDATE='" + NOTICEDATE + '\'' +
//                ", CBECBILLNO='" + CBECBILLNO + '\'' +
//                ", create_time=" + create_time +
//                ", modified_time=" + modified_time +
//                '}';
//    }
}
