package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.TaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoMapper extends Mapper<TaskInfo> {
    TaskInfo getByResourceId(@Param("resourceId")String resourceId);
    String hasThis(@Param("activityId")String activityId);

    String findResourceIdByActivityId(@Param("activityId")String activityId);

    TaskInfo findTaskInfoByActivityId(@Param("activityId")String activityId);

    TaskInfo getTaskInfoByResourceId(@Param("resourceId")String resourceId);
}