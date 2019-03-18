
package com.owinfo.web.webservice.org.tempuri;


import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.ArrayOfTaskOperationMessage;
import com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message.TaskOperationResult;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MixOperateTasksResponseMixOperateTasksResult_QNAME = new QName("http://tempuri.org/", "MixOperateTasksResult");
    private final static QName _InsertAccomplishedTasksAtasksJsonValue_QNAME = new QName("http://tempuri.org/", "atasksJsonValue");
    private final static QName _MixOperateTasksOfJsonTasks_QNAME = new QName("http://tempuri.org/", "tasks");
    private final static QName _InsertAccomplishedTasksResponseInsertAccomplishedTasksResult_QNAME = new QName("http://tempuri.org/", "InsertAccomplishedTasksResult");
    private final static QName _MixOperateTasksOfJsonResponseMixOperateTasksOfJsonResult_QNAME = new QName("http://tempuri.org/", "MixOperateTasksOfJsonResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MixOperateTasksResponse }
     * 
     */
    public MixOperateTasksResponse createMixOperateTasksResponse() {
        return new MixOperateTasksResponse();
    }

    /**
     * Create an instance of {@link MixOperateTasks }
     * 
     */
    public MixOperateTasks createMixOperateTasks() {
        return new MixOperateTasks();
    }

    /**
     * Create an instance of {@link MixOperateTasksOfJsonResponse }
     * 
     */
    public MixOperateTasksOfJsonResponse createMixOperateTasksOfJsonResponse() {
        return new MixOperateTasksOfJsonResponse();
    }

    /**
     * Create an instance of {@link MixOperateTasksOfJson }
     * 
     */
    public MixOperateTasksOfJson createMixOperateTasksOfJson() {
        return new MixOperateTasksOfJson();
    }

    /**
     * Create an instance of {@link InsertAccomplishedTasksResponse }
     * 
     */
    public com.owinfo.web.webservice.org.tempuri.InsertAccomplishedTasksResponse createInsertAccomplishedTasksResponse() {
        return new com.owinfo.web.webservice.org.tempuri.InsertAccomplishedTasksResponse();
    }

    /**
     * Create an instance of {@link InsertAccomplishedTasks }
     * 
     */
    public InsertAccomplishedTasks createInsertAccomplishedTasks() {
        return new InsertAccomplishedTasks();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskOperationResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "MixOperateTasksResult", scope = MixOperateTasksResponse.class)
    public JAXBElement<TaskOperationResult> createMixOperateTasksResponseMixOperateTasksResult(TaskOperationResult value) {
        return new JAXBElement<TaskOperationResult>(_MixOperateTasksResponseMixOperateTasksResult_QNAME, TaskOperationResult.class, MixOperateTasksResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "atasksJsonValue", scope = InsertAccomplishedTasks.class)
    public JAXBElement<String> createInsertAccomplishedTasksAtasksJsonValue(String value) {
        return new JAXBElement<String>(_InsertAccomplishedTasksAtasksJsonValue_QNAME, String.class, InsertAccomplishedTasks.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "tasks", scope = MixOperateTasksOfJson.class)
    public JAXBElement<String> createMixOperateTasksOfJsonTasks(String value) {
        return new JAXBElement<String>(_MixOperateTasksOfJsonTasks_QNAME, String.class, MixOperateTasksOfJson.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "InsertAccomplishedTasksResult", scope = InsertAccomplishedTasksResponse.class)
    public JAXBElement<String> createInsertAccomplishedTasksResponseInsertAccomplishedTasksResult(String value) {
        return new JAXBElement<String>(_InsertAccomplishedTasksResponseInsertAccomplishedTasksResult_QNAME, String.class, InsertAccomplishedTasksResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "MixOperateTasksOfJsonResult", scope = MixOperateTasksOfJsonResponse.class)
    public JAXBElement<String> createMixOperateTasksOfJsonResponseMixOperateTasksOfJsonResult(String value) {
        return new JAXBElement<String>(_MixOperateTasksOfJsonResponseMixOperateTasksOfJsonResult_QNAME, String.class, MixOperateTasksOfJsonResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTaskOperationMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "tasks", scope = MixOperateTasks.class)
    public JAXBElement<ArrayOfTaskOperationMessage> createMixOperateTasksTasks(ArrayOfTaskOperationMessage value) {
        return new JAXBElement<ArrayOfTaskOperationMessage>(_MixOperateTasksOfJsonTasks_QNAME, ArrayOfTaskOperationMessage.class, MixOperateTasks.class, value);
    }

}
