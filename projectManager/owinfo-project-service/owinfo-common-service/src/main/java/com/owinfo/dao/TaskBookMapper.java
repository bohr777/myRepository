package com.owinfo.dao;

import com.github.pagehelper.ISelect;
import com.owinfo.core.Mapper;
import com.owinfo.entity.BaseList;
import com.owinfo.entity.BasePlot;
import com.owinfo.entity.TaskBook;
import com.owinfo.entity.TaskBookResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskBookMapper extends Mapper<TaskBook>,ISelect{
    void deleteById1(@Param("id")String id);
    List<TaskBook> listForAll(@Param("mailNumber") String mailNumber,@Param("status") String status,@Param("competentDept")String competentDept,@Param("projectName")String projectName,@Param("tag")String tag,@Param("follows")List<String> follows);
    TaskBook getById(@Param("id")String id);
    Integer getTotal();
    void doSelect();
    String checkNum(@Param("mailNum") String mailNum);
    List<BasePlot> plotCount(@Param("competentDept")String competentDept,@Param("organizer")String organizer, @Param("from")String from, @Param("to")String to);
    List<BaseList> list1(@Param("competentDept")String competentDept,@Param("organizer")String organizer, @Param("from")String from, @Param("to")String to);

    List<TaskBookResult> taskBookResult(
            @Param("from")String from,@Param("to")String to,@Param("systemName")String systemName,
            @Param("detailsName")String detailsName,@Param("version")String version,@Param("competentDept")String competentDept,
            @Param("unitName")String unitName,@Param("status")String status,@Param("projectName")String projectName,@Param("page")Integer page,@Param("size")Integer size);
    Integer countTaskBookResult(
            @Param("from")String from,@Param("to")String to,@Param("systemName")String systemName,
            @Param("detailsName")String detailsName,@Param("version")String version,@Param("competentDept")String competentDept,
            @Param("unitName")String unitName,@Param("status")String status,@Param("projectName")String projectName);

    List<String> selectTaskbookByProjectName(@Param("projectName")String projectName);


}