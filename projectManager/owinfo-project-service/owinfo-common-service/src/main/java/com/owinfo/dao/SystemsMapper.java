package com.owinfo.dao;

import com.owinfo.core.Mapper;
import com.owinfo.entity.BaseList;
import com.owinfo.entity.BasePlot;
import com.owinfo.entity.Systems;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemsMapper extends Mapper<Systems>{
    void deleteById1(@Param("id")String id);
    List<Systems> listForAll();
    List<Systems> listByTaskBookId(@Param("taskbookId")String taskbookId);
    void deleteByTaskBookId(@Param("taskbookId")String taskbookId);
    String hasId(@Param("id")String id);
    List<BasePlot> plotCount(@Param("competentDept")String competentDept,@Param("organizer")String organizer, @Param("from")String from, @Param("to")String to);
    List<BaseList> list1(@Param("competentDept")String competentDept,@Param("organizer")String organizer, @Param("from")String from, @Param("to")String to);
}