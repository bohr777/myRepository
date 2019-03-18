
package com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.datacontract.schemas._2004._07.owinfo_message package.
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

    private final static QName _TaskOperationMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "TaskOperationMessage");
    private final static QName _TaskOperation_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "TaskOperation");
    private final static QName _ArrayOfTaskOperationMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "ArrayOfTaskOperationMessage");
    private final static QName _TaskMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "TaskMessage");
    private final static QName _TaskOperationResult_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "TaskOperationResult");
    private final static QName _TaskOperationResultErrorMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "ErrorMessage");
    private final static QName _TaskOperationMessageMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "Message");
    private final static QName _TaskMessageProcessId_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "ProcessId");
    private final static QName _TaskMessagePurpose_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "Purpose");
    private final static QName _TaskMessageActivityId_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "ActivityId");
    private final static QName _TaskMessageSourceId_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "SourceId");
    private final static QName _TaskMessageSendToName_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "SendToName");
    private final static QName _TaskMessageResourceId_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "ResourceId");
    private final static QName _TaskMessageStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", "Status");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.owinfo_message
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TaskOperationResult }
     * 
     */
    public TaskOperationResult createTaskOperationResult() {
        return new TaskOperationResult();
    }

    /**
     * Create an instance of {@link ArrayOfTaskOperationMessage }
     * 
     */
    public ArrayOfTaskOperationMessage createArrayOfTaskOperationMessage() {
        return new ArrayOfTaskOperationMessage();
    }

    /**
     * Create an instance of {@link TaskOperationMessage }
     * 
     */
    public TaskOperationMessage createTaskOperationMessage() {
        return new TaskOperationMessage();
    }

    /**
     * Create an instance of {@link TaskMessage }
     * 
     */
    public TaskMessage createTaskMessage() {
        return new TaskMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskOperationMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "TaskOperationMessage")
    public JAXBElement<TaskOperationMessage> createTaskOperationMessage(TaskOperationMessage value) {
        return new JAXBElement<TaskOperationMessage>(_TaskOperationMessage_QNAME, TaskOperationMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskOperation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "TaskOperation")
    public JAXBElement<TaskOperation> createTaskOperation(TaskOperation value) {
        return new JAXBElement<TaskOperation>(_TaskOperation_QNAME, TaskOperation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTaskOperationMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "ArrayOfTaskOperationMessage")
    public JAXBElement<ArrayOfTaskOperationMessage> createArrayOfTaskOperationMessage(ArrayOfTaskOperationMessage value) {
        return new JAXBElement<ArrayOfTaskOperationMessage>(_ArrayOfTaskOperationMessage_QNAME, ArrayOfTaskOperationMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "TaskMessage")
    public JAXBElement<TaskMessage> createTaskMessage(TaskMessage value) {
        return new JAXBElement<TaskMessage>(_TaskMessage_QNAME, TaskMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskOperationResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "TaskOperationResult")
    public JAXBElement<TaskOperationResult> createTaskOperationResult(TaskOperationResult value) {
        return new JAXBElement<TaskOperationResult>(_TaskOperationResult_QNAME, TaskOperationResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "ErrorMessage", scope = TaskOperationResult.class)
    public JAXBElement<String> createTaskOperationResultErrorMessage(String value) {
        return new JAXBElement<String>(_TaskOperationResultErrorMessage_QNAME, String.class, TaskOperationResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "Message", scope = TaskOperationMessage.class)
    public JAXBElement<TaskMessage> createTaskOperationMessageMessage(TaskMessage value) {
        return new JAXBElement<TaskMessage>(_TaskOperationMessageMessage_QNAME, TaskMessage.class, TaskOperationMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "ProcessId", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageProcessId(String value) {
        return new JAXBElement<String>(_TaskMessageProcessId_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "Purpose", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessagePurpose(String value) {
        return new JAXBElement<String>(_TaskMessagePurpose_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "ActivityId", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageActivityId(String value) {
        return new JAXBElement<String>(_TaskMessageActivityId_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "SourceId", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageSourceId(String value) {
        return new JAXBElement<String>(_TaskMessageSourceId_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "SendToName", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageSendToName(String value) {
        return new JAXBElement<String>(_TaskMessageSendToName_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "ResourceId", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageResourceId(String value) {
        return new JAXBElement<String>(_TaskMessageResourceId_QNAME, String.class, TaskMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/OWINFO.Message.WcfService", name = "Status", scope = TaskMessage.class)
    public JAXBElement<String> createTaskMessageStatus(String value) {
        return new JAXBElement<String>(_TaskMessageStatus_QNAME, String.class, TaskMessage.class, value);
    }

}
