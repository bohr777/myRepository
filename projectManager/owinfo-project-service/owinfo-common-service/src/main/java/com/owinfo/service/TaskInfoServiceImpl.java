package com.owinfo.service;

import com.owinfo.core.AbstractService;
import com.owinfo.dao.TaskInfoMapper;
import com.owinfo.entity.TaskInfo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/26.
 */
@RestController
@RequestMapping("/taskInfo")
public class TaskInfoServiceImpl extends AbstractService<TaskInfo> {

    private static final Logger logger = LoggerFactory.getLogger(TaskInfoServiceImpl.class);

    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @RequestMapping(value = "findTaskInfoByActivityId", method = RequestMethod.POST)
    public JSONObject findTaskInfoByActivityId(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = new JSONObject();
            String activityId = jsonObject.getString("activityId");
            TaskInfo taskInfo = taskInfoMapper.findTaskInfoByActivityId(activityId);
            if (null != taskInfo) {
                result.put("taskbookId", taskInfo.getResourceId());
                result.put("purpose", taskInfo.getPurpose());
                result.put("title", taskInfo.getTitle());
                result.put("prevAssignee", taskInfo.getPrevAssignee());
                return result;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    public String findResourceIdByActivityId(String activityId) {
        try {
            return taskInfoMapper.findResourceIdByActivityId(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    public TaskInfo getByResourceId(String resourceId) {
        try {
            return taskInfoMapper.getByResourceId(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public boolean saveOrUpdate(@RequestBody JSONObject jsonObject) {
        try {
            String activityId = jsonObject.getString("activityId");
            if (null != activityId && !"".equals(activityId)) {
                String flag = taskInfoMapper.hasThis(activityId);
                if (null != flag && !"".equals(flag)) {
                    this.update(jsonObject);
                } else {
                    this.save(jsonObject);
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
        return false;
    }
}
