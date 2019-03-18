package com.owinfo.actService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Description
 * @auther qiyong
 * @create 2018-12-13 18:28
 */
@FeignClient(value = "owinfo-project-activiti")
public interface ActivitiService {
    @RequestMapping(value = "/act/deployment",method = RequestMethod.POST)
    JSONObject deployment();
    @RequestMapping(value = "/act/startProcess",method = RequestMethod.POST)
    JSONObject startProcess(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/act/claimCompleteTask", method = RequestMethod.POST)
    JSONObject claimCompleteTask(@RequestBody Map<String,Object> param);
    @RequestMapping(value = "/act/claimCompleteTaskMany", method = RequestMethod.POST)
    JSONObject claimCompleteTaskMany(@RequestBody Map<String,Object> param);
    @RequestMapping(value = "/act/historicTasks", method = RequestMethod.POST)
    JSONObject historicTasks(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/act/runtimeTasks", method = RequestMethod.POST)
    JSONObject runtimeTasks(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/act/thisTask", method = RequestMethod.POST)
    JSONArray thisTask(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/act/variableSingle", method = RequestMethod.POST)
    Object variableSingle(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/act/getTaskManagerAssignee", method = RequestMethod.POST)
    JSONObject getTaskManagerAssignee(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/act/getVariable", method = RequestMethod.POST)
    Map<String,Object> getVariable(@RequestBody JSONObject jsonObject);
}
