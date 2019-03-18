package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.BG_Task_Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BG_Task_logMapper extends Mapper<BG_Task_Log> {
    List<BG_Task_Log> findRecord(@Param("guid")String guid);

    List<BG_Task_Log> findTaskId(@Param("taskId")String taskId);

    boolean insertSave(BG_Task_Log bg_task_log);

    boolean updateByGUID(BG_Task_Log bg_task_log);
}