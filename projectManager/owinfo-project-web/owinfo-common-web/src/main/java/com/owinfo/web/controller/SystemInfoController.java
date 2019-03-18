package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.service.SystemInfoService;
import com.owinfo.web.service.SystemsService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/26.
 */
@RestController
@RequestMapping("/systemInfo")
public class SystemInfoController {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfoController.class);

    @Autowired
    private SystemInfoService systemInfoService;
    @Autowired
    private SystemsService systemsService;
    @Autowired
    private BaseUserService baseUserService;


    @RequestMapping(value = "/findSystemBySystemName",method = RequestMethod.POST)
    public Result findSystemBySystemName(@RequestBody JSONObject jsonObject){
        boolean result;
        try{
            result = systemInfoService.findSystemBySystemName(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                String id = entity.getUuid();
                boolean flag = entity.getIsAdmin();
                if (!flag) {
                    return ResultGenerator.genFailResult("您没有该权限");
                }
                jsonObject.put("founderId", entity.getUuid());
                jsonObject.put("founderName", entity.getUserName());
                jsonObject.put("founderFullpath", entity.getAllPathName());
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
                jsonObject.put("isDelete", "0");
                jsonObject.put("createTime", new Date());
                jsonObject.put("finalModify", new Date());
            } else {
                return ResultGenerator.genFailResult("登陆失效");
            }
            jsonObject.put("id", UUID.randomUUID().toString());
            systemInfoService.save(jsonObject);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                String id = entity.getUuid();
                boolean flag = entity.getIsAdmin();
                if (!flag) {
                    return ResultGenerator.genFailResult("您没有该权限");
                }
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
                jsonObject.put("finalModify", new Date());
            }
            systemInfoService.update(jsonObject);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                String id = entity.getUuid();
                boolean flag = entity.getIsAdmin();
                if (!flag) {
                    return ResultGenerator.genFailResult("您没有该权限");
                }
            }
            return ResultGenerator.genSuccessResult(systemInfoService.deleteByIds(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "listForAll", method = RequestMethod.POST)
    public Result listForAll(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                String id = entity.getUuid();
                boolean flag = entity.getIsAdmin();
                if (!flag) {
                    return ResultGenerator.genFailResult("您没有该权限");
                }
            }
            Result result = ResultGenerator.genSuccessResult(systemInfoService.listForAll(jsonObject));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Result getById(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                String id = entity.getUuid();
                boolean flag = entity.getIsAdmin();
                if (!flag) {
                    return ResultGenerator.genFailResult("您没有该权限");
                }
            }
            return ResultGenerator.genSuccessResult(systemInfoService.getById(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genSuccessResult();
        }
    }

//    @RequestMapping(value = "getByName",method = RequestMethod.POST)
//    public Result getByName(@RequestBody JSONObject jsonObject){
//        try{
//            return ResultGenerator.genSuccessResult(systemInfoService.getByName(jsonObject));
//        }catch (Exception e){
//            return null;
//        }
//    }

    @RequestMapping(value = "getByName",method = RequestMethod.POST)
    public Result getByName(@RequestBody JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response){
        try{
            String systemLevel = null;
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            JSONObject systemLevelResult = null;
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("id", entity.getUuid());
                systemLevelResult = baseUserService.selectUserPathByGUID(jsonObject);
            }
            if ("Administration".equals(systemLevelResult.getString("systemLevel"))) {
                //署级
                systemLevel = "Administration";
            } else if ("Customhouse".equals(systemLevelResult.getString("systemLevel"))){
                //关级
                systemLevel = "Customhouse";
            }else if ("double".equals(systemLevelResult.getString("systemLevel"))){
                //是署级也是关级
                systemLevel = "double";
            }
            jsonObject.put("systemLevel",systemLevel);
            return ResultGenerator.genSuccessResult(systemsService.selectSystemLikeSystemName(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "getTree",method = RequestMethod.POST)
    public Result getTree(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult(systemInfoService.getTree(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    @RequestMapping(value = "getNetwork",method = RequestMethod.POST)
    public Result getNetwork(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }
    @RequestMapping(value = "listByName",method = RequestMethod.POST)
    public Result listByName(@RequestBody JSONObject jsonObject){
        try{
            return ResultGenerator.genSuccessResult(systemInfoService.listByName(jsonObject));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
            return ResultGenerator.genFailResult("获取失败");
        }
    }
//    @RequestMapping(value = "getRole",method = RequestMethod.POST)
//    public JSONArray getUserByRole(String role) {
//        try {
//            Element rt = AccreditXmlReaderHelper.getXmlUsersInRoles(
//                    "", OrganizationCategory.ORG_ALL_PATH_NAME,
//                    systemCode, AccreditCategory.Code,
//                    "CCIC_VIEW", AccreditCategory.Code,
//                    role, AccreditCategory.Code,
//                    DelegationCategories.All, ""
//            );
//            JSONArray users = new JSONArray();
//            NodeList ids = rt.getElementsByTagName("USER_GUID");
//            for (int i = 0; i < ids.getLength(); i++) {
//                users.add(ids.item(i).getFirstChild().getNodeValue());
//            }
//            return users;
//        } catch (Exception e) {
//            return null;
//        }
//    }



}
