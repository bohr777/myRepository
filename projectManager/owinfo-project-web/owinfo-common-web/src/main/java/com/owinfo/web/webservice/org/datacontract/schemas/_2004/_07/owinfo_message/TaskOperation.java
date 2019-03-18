
package com.owinfo.web.webservice.org.datacontract.schemas._2004._07.owinfo_message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TaskOperation�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="TaskOperation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Insert"/>
 *     &lt;enumeration value="Update"/>
 *     &lt;enumeration value="Cancel"/>
 *     &lt;enumeration value="Delete"/>
 *     &lt;enumeration value="Read"/>
 *     &lt;enumeration value="UnRead"/>
 *     &lt;enumeration value="Complete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TaskOperation")
@XmlEnum
public enum TaskOperation {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Insert")
    INSERT("Insert"),
    @XmlEnumValue("Update")
    UPDATE("Update"),
    @XmlEnumValue("Cancel")
    CANCEL("Cancel"),
    @XmlEnumValue("Delete")
    DELETE("Delete"),
    @XmlEnumValue("Read")
    READ("Read"),
    @XmlEnumValue("UnRead")
    UN_READ("UnRead"),
    @XmlEnumValue("Complete")
    COMPLETE("Complete");
    private final String value;

    TaskOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaskOperation fromValue(String v) {
        for (TaskOperation c: TaskOperation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
