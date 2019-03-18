package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.SetEntity;
import org.apache.ibatis.annotations.Param;

//关，署
public interface SetMapper extends Mapper<SetEntity>{

    boolean insertSetUp(SetEntity setEntity);

    SetEntity getSetUpById(@Param("userId")String userId);

}