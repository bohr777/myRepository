package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.Material;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MaterialMapper extends Mapper<Material> {
    List<Material> listForAll();
    Material getById(@Param("id") String id);
    List<Material> listByResourceId(@Param("id")String id);
    void deleteById1(@Param("resourceId")String resourceId);
    Integer isLocal(@Param("id") String id);
}