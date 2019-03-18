package com.owinfo.dao;


import com.owinfo.core.Mapper;
import com.owinfo.entity.STMOgus;
import com.owinfo.entity.STMSystemInfo;
import com.owinfo.entity.SystemInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface STMSystemInfoMapper extends Mapper<STMSystemInfo> {


//    List<SystemInfoResult> selectOrganizerLikeSystemName(@Param("systemName")String systemName);

    List<SystemInfoResult> selectSystemLikeSystemName(@Param("systemName")String systemName,@Param("systemLevel")String systemLevel);

    List<SystemInfoResult> selectSystemById(@Param("systemId")String systemId);

    List<SystemInfoResult> selectSystemBySystemName(@Param("systemName")String systemName);

    String findSystemIdBySystemName(@Param("systemName") String systemName);
}