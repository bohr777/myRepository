package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.BaseUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//人员信息
public interface BaseUserMapper extends Mapper<BaseUserEntity> {
    List<BaseUserEntity> selectUserByInfo(@Param("userName") String userName);

    List<BaseUserEntity> selectByRole(String role, String unit);

    List<BaseUserEntity> getByDept(@Param("parent") String parent);

    List<BaseUserEntity> listByName(@Param("name") String name);

    List<BaseUserEntity> selectUserByGUID(@Param("id") String id);

    List<BaseUserEntity> selectUserPathByGUID(@Param("id") String id);

    List<BaseUserEntity> selectUserById(@Param("id") String id);
}
