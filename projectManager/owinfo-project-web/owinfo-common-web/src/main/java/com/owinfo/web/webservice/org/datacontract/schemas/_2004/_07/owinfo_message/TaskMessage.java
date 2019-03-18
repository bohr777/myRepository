
package com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>TaskMessage complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TaskMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActivityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeliverTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Emergency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExpireTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastModifyTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Opened" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Operation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProcessId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReadTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ResourceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SendToName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TaskId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TaskLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TaskStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserGuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskMessage", propOrder = {
    "activityId",
    "applicationName",
    "deliverTime",
    "emergency",
    "expireTime",
    "lastModifyTime",
    "opened",
    "operation",
    "processId",
    "purpose",
    "readTime",
    "resourceId",
    "sendToName",
    "sourceId",
    "sourceName",
    "status",
    "systemCode",
    "taskId",
    "taskLevel",
    "taskStartTime",
    "title",
    "type",
    "url",
    "userGuid"
})
public class TaskMessage {

    @XmlElementRef(name = "ActivityId", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> activityId;
    @XmlElement(name = "ApplicationName", required = true, nillable = true)
    protected String applicationName;
    @XmlElement(name = "DeliverTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deliverTime;
    @XmlElement(name = "Emergency", required = true, nillable = true)
    protected String emergency;
    @XmlElement(name = "ExpireTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expireTime;
    @XmlElement(name = "LastModifyTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifyTime;
    @XmlElement(name = "Opened")
    protected boolean opened;
    @XmlElement(name = "Operation")
    protected Integer operation;
    @XmlElementRef(name = "ProcessId", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> processId;
    @XmlElementRef(name = "Purpose", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> purpose;
    @XmlElement(name = "ReadTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar readTime;
    @XmlElementRef(name = "ResourceId", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resourceId;
    @XmlElementRef(name = "SendToName", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sendToName;
    @XmlElementRef(name = "SourceId", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sourceId;
    @XmlElement(name = "SourceName", required = true, nillable = true)
    protected String sourceName;
    @XmlElementRef(name = "Status", namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElement(name = "SystemCode", required = true, nillable = true)
    protected String systemCode;
    @XmlElement(name = "TaskId", required = true, nillable = true)
    protected String taskId;
    @XmlElement(name = "TaskLevel")
    protected Integer taskLevel;
    @XmlElement(name = "TaskStartTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar taskStartTime;
    @XmlElement(name = "Title", required = true, nillable = true)
    protected String title;
    @XmlElement(name = "Type", required = true, nillable = true)
    protected String type;
    @XmlElement(name = "Url", required = true, nillable = true)
    protected String url;
    @XmlElement(name = "UserGuid", required = true, nillable = true)
    protected String userGuid;

    /**
     * ��ȡactivityId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getActivityId() {
        return activityId;
    }

    /**
     * ����activityId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setActivityId(JAXBElement<String> value) {
        this.activityId = value;
    }

    /**
     * ��ȡapplicationName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * ����applicationName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationName(String value) {
        this.applicationName = value;
    }

    /**
     * ��ȡdeliverTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeliverTime() {
        return deliverTime;
    }

    /**
     * ����deliverTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeliverTime(XMLGregorianCalendar value) {
        this.deliverTime = value;
    }

    /**
     * ��ȡemergency���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmergency() {
        return emergency;
    }

    /**
     * ����emergency���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmergency(String value) {
        this.emergency = value;
    }

    /**
     * ��ȡexpireTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpireTime() {
        return expireTime;
    }

    /**
     * ����expireTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpireTime(XMLGregorianCalendar value) {
        this.expireTime = value;
    }

    /**
     * ��ȡlastModifyTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * ����lastModifyTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifyTime(XMLGregorianCalendar value) {
        this.lastModifyTime = value;
    }

    /**
     * ��ȡopened���Ե�ֵ��
     * 
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * ����opened���Ե�ֵ��
     * 
     */
    public void setOpened(boolean value) {
        this.opened = value;
    }

    /**
     * ��ȡoperation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOperation() {
        return operation;
    }

    /**
     * ����operation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOperation(Integer value) {
        this.operation = value;
    }

    /**
     * ��ȡprocessId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProcessId() {
        return processId;
    }

    /**
     * ����processId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProcessId(JAXBElement<String> value) {
        this.processId = value;
    }

    /**
     * ��ȡpurpose���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPurpose() {
        return purpose;
    }

    /**
     * ����purpose���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPurpose(JAXBElement<String> value) {
        this.purpose = value;
    }

    /**
     * ��ȡreadTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReadTime() {
        return readTime;
    }

    /**
     * ����readTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReadTime(XMLGregorianCalendar value) {
        this.readTime = value;
    }

    /**
     * ��ȡresourceId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResourceId() {
        return resourceId;
    }

    /**
     * ����resourceId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResourceId(JAXBElement<String> value) {
        this.resourceId = value;
    }

    /**
     * ��ȡsendToName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSendToName() {
        return sendToName;
    }

    /**
     * ����sendToName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSendToName(JAXBElement<String> value) {
        this.sendToName = value;
    }

    /**
     * ��ȡsourceId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSourceId() {
        return sourceId;
    }

    /**
     * ����sourceId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSourceId(JAXBElement<String> value) {
        this.sourceId = value;
    }

    /**
     * ��ȡsourceName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * ����sourceName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceName(String value) {
        this.sourceName = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = value;
    }

    /**
     * ��ȡsystemCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * ����systemCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemCode(String value) {
        this.systemCode = value;
    }

    /**
     * ��ȡtaskId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * ����taskId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskId(String value) {
        this.taskId = value;
    }

    /**
     * ��ȡtaskLevel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTaskLevel() {
        return taskLevel;
    }

    /**
     * ����taskLevel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTaskLevel(Integer value) {
        this.taskLevel = value;
    }

    /**
     * ��ȡtaskStartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * ����taskStartTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTaskStartTime(XMLGregorianCalendar value) {
        this.taskStartTime = value;
    }

    /**
     * ��ȡtitle���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * ����title���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * ��ȡurl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * ����url���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * ��ȡuserGuid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserGuid() {
        return userGuid;
    }

    /**
     * ����userGuid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserGuid(String value) {
        this.userGuid = value;
    }

}
