
package com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TaskOperationMessage complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TaskOperationMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Message" type="{http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService}TaskMessage" minOccurs="0"/>
 *         &lt;element name="Operation" type="{http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService}TaskOperation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskOperationMessage", propOrder = {
    "message",
    "operation"
})
public class TaskOperationMessage {

    @XmlElementRef(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<TaskMessage> message;
    @XmlElement(name = "Operation")
    @XmlSchemaType(name = "string")
    protected TaskOperation operation;

    /**
     * ��ȡmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TaskMessage }{@code >}
     *     
     */
    public JAXBElement<TaskMessage> getMessage() {
        return message;
    }

    /**
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TaskMessage }{@code >}
     *     
     */
    public void setMessage(JAXBElement<TaskMessage> value) {
        this.message = value;
    }

    /**
     * ��ȡoperation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TaskOperation }
     *     
     */
    public TaskOperation getOperation() {
        return operation;
    }

    /**
     * ����operation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TaskOperation }
     *     
     */
    public void setOperation(TaskOperation value) {
        this.operation = value;
    }

}
