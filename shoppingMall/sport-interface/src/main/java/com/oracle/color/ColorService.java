package com.oracle.color;

import java.util.List;

import com.oracle.pojo.Color;

public interface ColorService {
	int deleteByPrimaryKey(Long id);

    int insert(Color record);

    int insertSelective(Color record);

    Color selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);
    
    List<Color> selectAllColor();
}
