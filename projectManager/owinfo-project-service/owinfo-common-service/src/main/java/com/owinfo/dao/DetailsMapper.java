package com.owinfo.dao;

import com.github.pagehelper.ISelect;
import com.owinfo.core.Mapper;
import com.owinfo.entity.BaseList;
import com.owinfo.entity.BasePlot;
import com.owinfo.entity.Details;
import com.owinfo.entity.TaskBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DetailsMapper extends Mapper<Details> {
    Details findDetailByDetailId(@Param("id")String id);

    void deleteById1(String id);

    List<Details> listForAll();

    List<Details> listByTaskBookId(@Param("taskbookId") String taskbookId);

    List<Details> listBySystemId(@Param("systemId") String systemId);

    void deleteByTaskBookId(@Param("taskbookId") String taskbookId);

    void deleteBySystemId(@Param("systemId") String systemId);

    String hasId(@Param("id") String id);

    Integer numByStatus(@Param("taskbookId") String taskbookId, @Param("status") String status);

    Integer frequencyBySystemId(@Param("systemId") String systemId);

    Integer frequencyByid(@Param("id") String id);

    String checkNum(@Param("detailsNum") String detailsNum);

    List<BasePlot> plotCount(@Param("competentDept") String competentDept, @Param("organizer") String organizer, @Param("from") String from, @Param("to") String to);

    List<BaseList> list1(@Param("competentDept") String competentDept, @Param("organizer") String organizer, @Param("from") String from, @Param("to") String to);

    Integer findSystemCountBySystemName(@Param("systemName") String systemName);

    List<Details> findDetailsLikeDetailsName(@Param("detailsName") String detailsName);

    List<Map<String,Object>> findDetailsLikeVersion(@Param("version") String version);

    List<Details> findDetailByTaskbookId(@Param("taskbookId") String taskbookId);
}