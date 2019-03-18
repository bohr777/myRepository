package com.owinfo.dao;

import org.apache.ibatis.annotations.Param;
import com.owinfo.core.Mapper;
import com.owinfo.entity.TaskAuth;

public interface TaskAuthMapper extends Mapper<TaskAuth>{
    TaskAuth getByNode(@Param("node")String node);
}