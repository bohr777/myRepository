package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.PB_ProjectBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PB_ProjectBaseMapper extends Mapper<PB_ProjectBase> {
    List<PB_ProjectBase> queryProject(@Param("subject") String subject, @Param("follows") List<String> follows,@Param("systemLevel") String systemLevel);
    List<PB_ProjectBase> queryProjectShu(@Param("subject") String subject, @Param("parent")String parent);
    List<PB_ProjectBase> queryProjectGuan(@Param("subject") String subject, @Param("parent")String parent);
    List<PB_ProjectBase> getByDept(@Param("follows") List<String> follows,@Param("parent")String parent);
    List<PB_ProjectBase> getByDeptShu(@Param("follows") List<String> follows,@Param("parent")String parent);
    List<PB_ProjectBase> getByDeptGuan(@Param("follows") List<String> follows,@Param("parent")String parent);
    List<PB_ProjectBase> listByName(@Param("name") String name,@Param("follows")List<String> follows,@Param("type")String type);
}