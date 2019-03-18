package com.owinfo.web.controller;

import cn.gov.customs.casp.sdk.h4a.AccreditXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.IAccreditReaderIsUserInRolesCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.UserCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import cn.gov.customs.casp.sdk.h4a.sso.passport.util.PassportManager;
import com.owinfo.web.config.util.PassportSSO;
import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.BaseUserService;
import com.owinfo.web.service.SetService;
import com.owinfo.web.service.TaskBookBiz;
import com.owinfo.web.service.TaskBookService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by liyue on 2017/10/14.
 */
@RestController
@RequestMapping("/taskbook")
public class TaskBookController {

    private static final Logger logger = LoggerFactory.getLogger(TaskBookController.class);

    @Autowired
    private TaskBookService taskBookService;
    @Autowired
    private TaskBookBiz taskBookBiz;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private SetService setService;
    @Value("${systemCode}")
    private String systemCode;
    @Value("${adminCode}")
    private String adminCode;
    @Value("${fontAddress}")
    private String fontAddress;
    @Value("${managerCode}")
    private String managerCode;

    @RequestMapping(value = "deskGet", method = RequestMethod.POST)
    public Result deskGet(@RequestBody JSONObject jsonObject) {
        ThrInOneEntity entity = new ThrInOneEntity();
        if (jsonObject.getString("username") != null && !"".equals(jsonObject.getString("username"))) {
            entity.setUuid(jsonObject.getString("username"));
        }
        String uuid = entity.getUuid();
        JSONObject user = baseUserService.selectUserById(JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}"));
        entity.setUserName(user.getString("userName"));
        entity.setAllPathName(user.getString("userPath"));

        if (jsonObject.has("auth") && ("任务拆分负责人".equals(jsonObject.getString("auth")) || "任务拆分".equals(jsonObject.getString("auth"))) && null != entity) {
            String path = entity.getAllPathName();
            String name = entity.getUserName();
            jsonObject.put("userPath", path.substring(0, path.lastIndexOf("\\")));
        }
        return ResultGenerator.genSuccessResult(taskBookBiz.deskGet(jsonObject));
    }

    @RequestMapping(value = "/deskUpdate", method = RequestMethod.POST)
    public Result deskUpdate(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        try {
            ThrInOneEntity entity = new ThrInOneEntity();
            if (jsonObject.getString("username") != null && !"".equals(jsonObject.getString("username"))) {
                entity.setUuid(jsonObject.getString("username"));
            }
            String uuid = entity.getUuid();
            JSONObject user = baseUserService.selectUserById(JSONObject.fromObject("{\"id\":" + "\"" + uuid + "\"}"));
            entity.setUserName(user.getString("userName"));
            entity.setAllPathName(user.getString("userPath"));
            if (null != entity) {
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
        return ResultGenerator.genSuccessResult(taskBookBiz.whollyUpdate(jsonObject));
    }


    @RequestMapping(value = "/tempSave", method = RequestMethod.POST)
    public Result tempSave(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("founderId", entity.getUuid());
                jsonObject.put("founderName", entity.getUserName());
                jsonObject.put("founderFullPath", entity.getAllPathName());
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
        return ResultGenerator.genSuccessResult(taskBookBiz.tempSave(jsonObject));
    }

    @RequestMapping(value = "/whollySave", method = RequestMethod.POST)
    public Result whollySave(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("founderId", entity.getUuid());
                jsonObject.put("founderName", entity.getUserName());
                jsonObject.put("founderFullPath", entity.getAllPathName());
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
        return ResultGenerator.genSuccessResult(taskBookBiz.whollySave(jsonObject));
    }

    @RequestMapping(value = "/whollyUpdate", method = RequestMethod.POST)
    public Result whollyUpdate(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
        return ResultGenerator.genSuccessResult(taskBookBiz.whollyUpdate(jsonObject));
    }

    @RequestMapping(value = "/whollyDelete", method = RequestMethod.POST)
    public Result whollyDelete(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                jsonObject.put("finalModifierId", entity.getUuid());
                jsonObject.put("finalModifierName", entity.getUserName());
                jsonObject.put("finalModifierFullpath", entity.getAllPathName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }

        return ResultGenerator.genSuccessResult(taskBookBiz.whollyDelete(jsonObject));
    }

    @RequestMapping(value = "whollyGet", method = RequestMethod.POST)
    public Result whollyGet(@RequestBody JSONObject jsonObject) {
        HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
        ThrInOneEntity entity = new ThrInOneEntity();
        if (jsonObject.has("auth") && ("任务拆分负责人".equals(jsonObject.getString("auth")) || "任务拆分".equals(jsonObject.getString("auth"))) && null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
            String path = entity.getAllPathName();
            String name = entity.getUserName();
//            jsonObject.put("userPath",path.substring(0,path.lastIndexOf(name)-1));
            jsonObject.put("userPath", path.substring(0, path.lastIndexOf("/")));
        }
//        if(null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))){
//            jsonObject.put("name",entity.getUserName());
//            jsonObject.put("path",entity.getAllPathName().replaceAll("/","\\\\"));
//            jsonObject.put("uuid",entity.getUuid());
//            String allPathName = entity.getAllPathName().replaceAll("/","\\\\");
//            String assignee = allPathName.substring(0,StringUtils.ordinalIndexOf(allPathName, "\\", 3));
//            jsonObject.put("taskManagerUuid",this.getUsersByRole(assignee, managerCode));
//        }

        return ResultGenerator.genSuccessResult(taskBookBiz.whollyGet(jsonObject));
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(taskBookService.deleteByIds(jsonObject));
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public Result getById(@RequestBody JSONObject jsonObject) {
        return ResultGenerator.genSuccessResult(taskBookService.getById(jsonObject));
    }

    @RequestMapping(value = "/listForAll", method = RequestMethod.POST)
    public Result listForAll(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
            }
            jsonObject.put("followId", follow);
            return ResultGenerator.genSuccessResult(taskBookService.listForAll(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/createId", method = RequestMethod.POST)
    public JSONObject createId() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", UUID.randomUUID().toString());
        return jsonObject;
    }


    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public void checkLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "token", required = false) String token) {
        try {
            String type = request.getParameter("type");
            if (null != type && "logout".equals(type)) {
                return;
            }
            PassportSSO passportSSO = new PassportSSO();
            if (null == request.getSession().getAttribute("currentUser")) {
                passportSSO.Login(PassportManager.getTicket(request, response), request, response, "");
            }
            String JSESSIONID = request.getSession().getId();
//            JSONObject result = new JSONObject();
//            result.put("JSESSIONID", JSESSIONID);
            HttpSession session = SessionContext.getSession(JSESSIONID);
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
//                result.put("userName", entity.getUuid());
//                result.put("name", entity.getUserName());
//                result.put("path", entity.getAllPathName());
//                boolean flag = isUserInRole(entity.getUuid(), adminCode, systemCode);
//                result.put("isAdmin", flag);
//                entity.setIsAdmin(flag);
                response.sendRedirect(fontAddress + "?userName=" + URLEncoder.encode(entity.getUuid()) + "&JSESSIONID=" + URLEncoder.encode(JSESSIONID) + "&name=" + URLEncoder.encode(entity.getUserName(), "utf-8") + "&path=" + URLEncoder.encode(entity.getAllPathName(), "utf-8") + "&isAdmin=" + isUserInRole(entity.getUuid(), adminCode, systemCode));

            } else {
                response.sendRedirect(fontAddress + "?error=403");
            }
//            return "<script>top.postMessage('" + result + "','*');</script>";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
//            return null;
        }
    }


    @RequestMapping(value = "checkNum", method = RequestMethod.POST)
    public boolean checkNum(@RequestBody JSONObject jsonObject) {
        try {
            if (jsonObject.has("mailNum")) {
                return taskBookService.checkNum(jsonObject);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return false;
        }
    }

    @RequestMapping(value = "taskBookForm", method = RequestMethod.POST)
    public Result taskBookForm() {
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "unitFormById", method = RequestMethod.POST)
    public Result unitFormById() {
        return ResultGenerator.genSuccessResult();
    }


    public boolean isUserInRole(String id, String role, String systemCode) {
        try {
            boolean flag = AccreditXmlReaderHelper.isUserInRolesXml(id, UserCategory.USER_GUID,
                    "", OrganizationCategory.ORG_ALL_PATH_NAME,
                    systemCode, AccreditCategory.Code,
                    "CCIC_VIEW", AccreditCategory.Code,
                    role, AccreditCategory.Code,
                    DelegationCategories.All);
            return flag;
        } catch (IAccreditReaderIsUserInRolesCupaaFaultArgsFaultFaultMessage iAccreditReaderIsUserInRolesCupaaFaultArgsFaultFaultMessage) {
            return false;
        }
    }


    @RequestMapping(value = "/taskBookResult", method = RequestMethod.POST)
    public Result taskBookResult(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
            }
            jsonObject.put("followId", follow);
            JSONObject result = taskBookService.taskBookResult(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/totalResult", method = RequestMethod.POST)
    public Result totalResult(@RequestBody JSONObject jsonObject) {
        try {
            String follow = "";
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                follow = entity.getUuid();
//                jsonObject.put("name",entity.getUserName());
//                jsonObject.put("path",entity.getAllPathName().replaceAll("/","\\\\"));
                jsonObject.put("uuid", entity.getUuid());
//                String allPathName = entity.getAllPathName().replaceAll("/","\\\\");
//                String assignee = allPathName.substring(0,StringUtils.ordinalIndexOf(allPathName, "\\", 3));
//                jsonObject.put("taskManagerUuid",this.getUsersByRole(assignee, managerCode));
            }
            jsonObject.put("followId", follow);
            JSONObject result = taskBookService.taskBookResult(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    @RequestMapping(value = "/selectTaskbookByProjectName", method = RequestMethod.POST)
    public Result selectTaskbookByProjectName(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject result = taskBookService.selectTaskbookByProjectName(jsonObject);
            return ResultGenerator.genSuccessResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

    @RequestMapping(value = "/getUsersByRole", method = RequestMethod.POST)
    public JSONObject getUsersByRole(JSONObject jsonObject) {
        try {
            JSONObject json = new JSONObject();
            String path = jsonObject.getString("path");
            String role = jsonObject.getString("role");
            Element rt = AccreditXmlReaderHelper.getXmlUsersInRoles(
                    path, OrganizationCategory.ORG_ALL_PATH_NAME,
                    systemCode, AccreditCategory.Code,
                    "CCIC_VIEW", AccreditCategory.Code,
                    role, AccreditCategory.Code,
                    DelegationCategories.All, ""
            );
            StringBuffer result = new StringBuffer();
            NodeList ids = rt.getElementsByTagName("USER_GUID");
            for (int i = 0; i < ids.getLength(); i++) {
                if (i == ids.getLength() - 1) {
                    result.append(ids.item(i).getFirstChild().getNodeValue());
                } else {
                    result.append(ids.item(i).getFirstChild().getNodeValue() + ",");
                }
            }
            json.put("uuid", result.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return null;
        }
    }

}
