package com.owinfo.dao;


import com.owinfo.core.Mapper;
import com.owinfo.entity.STMSystemInfo;
import com.owinfo.entity.TaskBookSystemInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;

import java.util.List;

public interface TaskBookSystemInfoMapper extends Mapper<TaskBookSystemInfo> {

    int deleteByTaskBookId(@Param("id")String id);

    List<TaskBookSystemInfo> selectSystemByTaskbookId(@Param("taskbookId")String taskbookId);

    Integer selectCountById(@Param("taskbookId")String taskbookId,@Param("systemId")String systemId);
}