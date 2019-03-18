package com.owinfo.configurer.Dto.EPT902;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by HHYang on 2017/5/12.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "MESSAGEBODY")
@XmlType(propOrder = {"BODYMASTER"})
public class MESSAGEBODY {

    private BODYMASTER BODYMASTER;

    public com.owinfo.configurer.Dto.EPT902.BODYMASTER getBODYMASTER() {
        return BODYMASTER;
    }

    public void setBODYMASTER(com.owinfo.configurer.Dto.EPT902.BODYMASTER BODYMASTER) {
        this.BODYMASTER = BODYMASTER;
    }
}
