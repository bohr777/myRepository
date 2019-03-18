
package com.owinfo.web.webservice.org.tempuri;

import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.TaskOperationResult;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;

/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MixOperateTasksResult" type="{http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService}TaskOperationResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mixOperateTasksResult"
})
@XmlRootElement(name = "MixOperateTasksResponse")
public class MixOperateTasksResponse {

    @XmlElementRef(name = "MixOperateTasksResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<TaskOperationResult> mixOperateTasksResult;

    /**
     * ��ȡmixOperateTasksResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TaskOperationResult }{@code >}
     *     
     */
    public JAXBElement<TaskOperationResult> getMixOperateTasksResult() {
        return mixOperateTasksResult;
    }

    /**
     * ����mixOperateTasksResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TaskOperationResult }{@code >}
     *     
     */
    public void setMixOperateTasksResult(JAXBElement<TaskOperationResult> value) {
        this.mixOperateTasksResult = value;
    }

}
