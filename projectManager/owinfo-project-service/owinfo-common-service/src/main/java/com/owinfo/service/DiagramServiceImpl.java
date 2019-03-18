package com.owinfo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.owinfo.dao.DetailsMapper;
import com.owinfo.dao.SystemsMapper;
import com.owinfo.dao.TaskBookMapper;
import com.owinfo.entity.BaseList;
import com.owinfo.entity.BasePlot;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */
@RestController
@RequestMapping("/diagram")
public class DiagramServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DiagramServiceImpl.class);

    @Autowired
    private TaskBookMapper taskBookMapper;
    @Autowired
    private SystemsMapper systemsMapper;
    @Autowired
    private DetailsMapper detailsMapper;

//    @RequestMapping(value = "plotCount", method = RequestMethod.POST)
//    public JSONObject plotCount(@RequestBody JSONObject jsonObject) {
//        try {
//            String paramType = jsonObject.getString("paramType");
//            String from = "";
//            String to = "";
//            if (!"".equals(jsonObject.getString("from"))) {
//                from = jsonObject.getString("from") + " 00:00:00";
//            }
//            if (!"".equals(jsonObject.getString("to"))) {
//                to = jsonObject.getString("to") + " 23:59:59";
//            }
//            String organizer=jsonObject.getString("organizer");
//            String competentDept = jsonObject.getString("competentDept");
//            if ("0".equals(paramType) || "".equals(paramType)) {
//                List<BasePlot> plots = taskBookMapper.plotCount(competentDept,organizer, from, to);
//                JSONObject result = new JSONObject();
//                result.put("0", new JSONArray());
//                result.put("1", new JSONArray());
//                for (BasePlot plot : plots) {
//                    String status1 = "";
//                    String status2 = "";
//                    if (null != plot && null != plot.getStatus1() && null != plot.getStatus2()) {
//                        status1 = plot.getStatus1();
//                        status2 = plot.getStatus2();
//                        if (!"".equals(status1) && !"".equals(status2) && !"0".equals(status2) && status1.equals(status2)) {
//                            result.getJSONArray("1").add(plot.getCreateTime());
//                        } else {
//                            result.getJSONArray("0").add(plot.getCreateTime());
//                        }
//                    } else {
//                        result.getJSONArray("0").add(plot.getCreateTime());
//                    }
//                }
//                return result;
//            } else if ("1".equals(paramType)) {
//                List<BasePlot> plots = systemsMapper.plotCount(competentDept,organizer, from, to);
//                JSONObject result = new JSONObject();
//                result.put("0", new JSONArray());
//                result.put("1", new JSONArray());
//                for (BasePlot plot : plots) {
//                    if (!"0".equals(plot.getStatus1()) && "0".equals(plot.getStatus2())) {
//                        result.getJSONArray("1").add(plot.getCreateTime());
//                    } else {
//                        result.getJSONArray("0").add(plot.getCreateTime());
//                    }
//                }
//                return result;
//            } else if ("2".equals(paramType)) {
//                List<BasePlot> plots = detailsMapper.plotCount(competentDept,organizer, from, to);
//                JSONObject result = new JSONObject();
//                result.put("0", new JSONArray());
//                result.put("1", new JSONArray());
//                result.put("2", new JSONArray());
//                result.put("3", new JSONArray());
//                result.put("4", new JSONArray());
//                for (BasePlot plot : plots) {
//                    result.getJSONArray(plot.getStatus1()).add(plot.getCreateTime());
//                }
//                return result;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            return null;
//        }
//    }

//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    public JSONObject list(@RequestBody JSONObject jsonObject) {
//        try {
//            String from = "";
//            String to = "";
//            if (!"".equals(jsonObject.getString("from"))) {
//                from = jsonObject.getString("from") + " 00:00:00";
//            }
//            if (!"".equals(jsonObject.getString("to"))) {
//                to = jsonObject.getString("to") + " 23:59:59";
//            }
//            Integer page=0;
//            Integer size=10;
//            if(!"".equals(jsonObject.getString("page"))){
//                page=Integer.parseInt(jsonObject.getString("page"));
//            }
//            if(!"".equals(jsonObject.getString("size"))){
//                size=Integer.parseInt(jsonObject.getString("size"));
//            }
//            PageHelper.startPage(page,size);
//            String competentDept = jsonObject.getString("competentDept");
//            String organizer=jsonObject.getString("organizer");
//            List<BaseList> taskbook=taskBookMapper.list1(competentDept,organizer,from,to);
//            List<BaseList> system=systemsMapper.list1(competentDept,organizer,from,to);
//            List<BaseList> detail=detailsMapper.list1(competentDept,organizer,from,to);
//            if(null!=taskbook.get(0).getDept()&&!"".equals(taskbook.get(0).getDept())){
//                for(int i=0;i<taskbook.size();i++){
//                    for(int j=0;j<system.size();j++){
//                        BaseList t=taskbook.get(i);
//                        BaseList s=system.get(j);
//                        if(t.getDept().equals(s.getDept())){
//                            t.setSystemCount(s.getSystemCount());
//                            break;
//                        }
//                    }
//                }
//                for(int i=0;i<taskbook.size();i++){
//                    for(int j=0;j<detail.size();j++){
//                        BaseList t=taskbook.get(i);
//                        BaseList d=detail.get(j);
//                        if(t.getDept().equals(d.getDept())){
//                            t.setDetailCount(d.getDetailCount());
//                            break;
//                        }
//                    }
//                    taskbook.get(i).setDept(taskbook.get(i).getDept().split("name:")[1].split(",fullpath")[0]);
//                }
//            }else{
//                for(int i=0;i<taskbook.size();i++){
//                    for(int j=0;j<system.size();j++){
//                        BaseList t=taskbook.get(i);
//                        BaseList s=system.get(j);
//                        if(t.getOrganizer().equals(s.getOrganizer())){
//                            t.setSystemCount(s.getSystemCount());
//                            break;
//                        }
//                    }
//                }
//                for(int i=0;i<taskbook.size();i++){
//                    for(int j=0;j<detail.size();j++){
//                        BaseList t=taskbook.get(i);
//                        BaseList d=detail.get(j);
//                        if(t.getOrganizer().equals(d.getOrganizer())){
//                            t.setDetailCount(d.getDetailCount());
//                            break;
//                        }
//                    }
//                    taskbook.get(i).setDept(taskbook.get(i).getOrganizer());
//                }
//            }
//            PageInfo info=new PageInfo(taskbook);
//            return JSONObject.fromObject(info);
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
