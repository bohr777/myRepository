package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.SystemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface SystemInfoMapper extends Mapper<SystemInfo> {
    List<SystemInfo> listForAll(@Param("systemName") String systemName);

    void deleteById1(@Param("id") String id);

    List<SystemInfo> getByName(@Param("name") String name);

    List<SystemInfo> getByDept(@Param("parent")String parent);

    List<SystemInfo> listByName(@Param("name")String name);

    int findSystemBySystemName(@Param("systemName")String systemName);
}