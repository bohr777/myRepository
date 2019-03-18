
package com.owinfo.web.webservice.org.tempuri;

import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.ArrayOfTaskOperationMessage;

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
 *         &lt;element name="tasks" type="{http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService}ArrayOfTaskOperationMessage" minOccurs="0"/>
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
    "tasks"
})
@XmlRootElement(name = "MixOperateTasks")
public class MixOperateTasks {

    @XmlElementRef(name = "tasks", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfTaskOperationMessage> tasks;

    /**
     * ��ȡtasks���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTaskOperationMessage }{@code >}
     *     
     */
    public JAXBElement<ArrayOfTaskOperationMessage> getTasks() {
        return tasks;
    }

    /**
     * ����tasks���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTaskOperationMessage }{@code >}
     *     
     */
    public void setTasks(JAXBElement<ArrayOfTaskOperationMessage> value) {
        this.tasks = value;
    }

}
