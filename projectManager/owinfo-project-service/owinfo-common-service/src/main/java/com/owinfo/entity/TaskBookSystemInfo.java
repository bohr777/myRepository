
package com.owinfo.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BG_TaskBook_SystemInfo")
public class TaskBookSystemInfo {
    @Id
    private String id;

    private String taskbookId;

    private String systemId;

    private String isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskbookId() {
        return taskbookId;
    }

    public void setTaskbookId(String taskbookId) {
        this.taskbookId = taskbookId == null ? null : taskbookId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "TaskBookSystemInfo{" +
                "id='" + id + '\'' +
                ", taskbookId='" + taskbookId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }
}