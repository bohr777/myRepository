package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.TaskOpinion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskOpinionMapper extends Mapper<TaskOpinion>{
    List<TaskOpinion> listByResourceId(@Param("resourceId")String resourceId);


    List<TaskOpinion> selectOpinionByTaskId(@Param("resourceId")String resourceId);



}