package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.CustEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//部门
public interface CustMapper extends Mapper<CustEntity>{
    List<CustEntity> selectByInfo(@Param("displayName") String displayName);
    List<CustEntity> selectByInfoGuan(@Param("displayName") String displayName);
    List<CustEntity> selectByInfoShu(@Param("displayName") String displayName);
    List<CustEntity> getChild(@Param("parent")String parent,@Param("level")Integer level);
    List<CustEntity> listByName(@Param("name")String name);
}
