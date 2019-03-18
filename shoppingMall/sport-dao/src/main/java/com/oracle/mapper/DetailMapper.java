package com.oracle.mapper;

import com.oracle.pojo.Detail;

public interface DetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Detail record);

    int insertSelective(Detail record);

    Detail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Detail record);

    int updateByPrimaryKey(Detail record);
}