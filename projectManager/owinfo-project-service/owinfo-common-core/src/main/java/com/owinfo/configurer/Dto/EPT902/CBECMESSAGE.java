package com.owinfo.configurer.Dto.EPT902;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by liyue on 2017/5/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CBECMESSAGE")
@XmlType(propOrder = {"MESSAGEHEAD", "MESSAGEBODY"})
public class CBECMESSAGE {

    private MESSAGEHEAD MESSAGEHEAD;

    private MESSAGEBODY MESSAGEBODY;

    public MESSAGEHEAD getMESSAGEHEAD() {
        return MESSAGEHEAD;
    }

    public void setMESSAGEHEAD(MESSAGEHEAD MESSAGEHEAD) {
        this.MESSAGEHEAD = MESSAGEHEAD;
    }

    public MESSAGEBODY getMESSAGEBODY() {
        return MESSAGEBODY;
    }

    public void setMESSAGEBODY(MESSAGEBODY MESSAGEBODY) {
        this.MESSAGEBODY = MESSAGEBODY;
    }
}
