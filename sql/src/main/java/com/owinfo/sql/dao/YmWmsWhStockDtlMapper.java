package com.owinfo.sql.dao;

import com.owinfo.sql.bean.YmWmsWhStockDtl;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Mapper
public interface YmWmsWhStockDtlMapper {

    List selectAll();

    List selectAllByPage(Integer page);
    int deleteByPrimaryKey(Long rid);

    int insert(YmWmsWhStockDtl record);

    int insertSelective(YmWmsWhStockDtl record);

    YmWmsWhStockDtl selectByPrimaryKey(Long rid);

    int updateByPrimaryKeySelective(YmWmsWhStockDtl record);

    int updateByPrimaryKey(YmWmsWhStockDtl record);
}