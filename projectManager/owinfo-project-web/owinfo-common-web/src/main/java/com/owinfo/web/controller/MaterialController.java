package com.owinfo.web.controller;

import com.owinfo.web.config.util.ThrInOneEntity;
import com.owinfo.web.core.Result;
import com.owinfo.web.core.ResultGenerator;
import com.owinfo.web.core.SessionContext;
import com.owinfo.web.service.MaterialService;
import com.owinfo.web.util.FileHandleUtil;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * Created by liyue on 2017/10/14.
 */
@Controller
@RequestMapping("/material")
public class MaterialController {

    private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;
    @Value("${filePath}")
    private String filePath;
    @Value("${HBUrl}")
    private String HBUrl;

    //    @RequestMapping(value = "/download",method = RequestMethod.POST)
//    ResponseEntity<InputStreamResource> download(@RequestParam("id")String id){
//        return materialService.download(id);
//    }
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("resourceId") String resourceId, @RequestParam("JSESSIONID") String JSESSIONID, String uuid) {
        JSONObject jsonObject = new JSONObject();
        try {
            ThrInOneEntity entity = new ThrInOneEntity();
            if (!"undefined".equals(JSESSIONID)) {
                HttpSession session = SessionContext.getSession(JSESSIONID);
                if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                    jsonObject.put("creator", entity.getUuid());
                }
            } else if (uuid != null && "undefined".equals(JSESSIONID)) {
                jsonObject.put("creator", uuid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("登陆失效");
        }
        String name = file.getOriginalFilename();
        String[] temp = name.split("\\.");
        String next = temp[temp.length - 1];
        String finalName = null;
        try {
            finalName = FileHandleUtil.upload(file, next, filePath);
            if ("0".equals(finalName)) {
                return ResultGenerator.genFailResult("文件为空，禁止上传");
            }
            String id = UUID.randomUUID().toString();
            jsonObject.put("id", id);
            jsonObject.put("resourceId", resourceId);
            jsonObject.put("sortId", "0");
            jsonObject.put("clazz", "Accessory");
            jsonObject.put("title", name);
            jsonObject.put("originalName", name);
            jsonObject.put("filePath", finalName);
            jsonObject.put("pageQuantity", "0");
            jsonObject.put("applicationName", "BG");
            jsonObject.put("programName", "BG");
            jsonObject.put("lastUploadTag", new Date());
            jsonObject.put("createDatetime", new Date());
            jsonObject.put("lastModifyTime", new Date());
            materialService.save(jsonObject);
            JSONObject r = new JSONObject();
            r.put("id", id);
            return ResultGenerator.genSuccessResult(r);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            return ResultGenerator.genFailResult("上传失败");
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error(e1.toString());
            return ResultGenerator.genFailResult("上传失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public /*ResponseEntity<InputStreamResource>*/Result download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
        // Get your file stream from wherever.
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        ServletContext context = request.getServletContext();

        // get MIME type of the file
        // set to binary type if MIME mapping not found
        String mimeType = "application/octet-stream; charset=utf-8";

        // set content attributes for the response
        response.setContentType(mimeType);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    URLEncoder.encode(materialService.getById(jsonObject).getString("title"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            int i = 1;
        }
        response.setHeader(headerKey, headerValue);
        if (materialService.isLocal(jsonObject)) {

            // Copy the stream to the response's output stream.
            if (filePath.startsWith("smb")) {
                String fullPath = materialService.getById(jsonObject).getString("filePath");
                SmbFile downloadFile = null;
                try {
                    downloadFile = new SmbFile(fullPath);
                    response.setContentLength((int) downloadFile.length());
                } catch (MalformedURLException e) {
                    int i = 2;
                } catch (SmbException e) {
                    int i = 3;
                }
                try {
                    InputStream myStream = new SmbFileInputStream(fullPath);
                    IOUtils.copy(myStream, response.getOutputStream());
                    response.flushBuffer();
                    return ResultGenerator.genSuccessResult("SUCCESS");
                } catch (IOException e) {
                    return ResultGenerator.genFailResult("下载失败");
                }
            } else {
                String fullPath = materialService.getById(jsonObject).getString("filePath");
                File downloadFile = null;
                downloadFile = new File(fullPath);
                response.setContentLength((int) downloadFile.length());
                try {
                    InputStream myStream = new FileInputStream(fullPath);
                    IOUtils.copy(myStream, response.getOutputStream());
                    response.flushBuffer();
                    return ResultGenerator.genSuccessResult("SUCCESS");
                } catch (IOException e) {
                    return ResultGenerator.genFailResult("下载失败");
                }
            }
        } else {
            try {
                String base64 = downloadOther(id, HBUrl);
                if ("".equals(base64)) {
                    return ResultGenerator.genFailResult("该文件已被删除");
                }
                byte[] file = new BASE64Decoder().decodeBuffer(base64);
                response.getOutputStream().write(file);
                response.flushBuffer();
                return ResultGenerator.genSuccessResult("SUCCESS");

            } catch (IOException e) {
                return ResultGenerator.genFailResult("下载失败");
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    public Result delete(@RequestBody JSONObject jsonObject) {
        Result result = null;
        if (jsonObject.has("ids")) {
            result = ResultGenerator.genSuccessResult(materialService.delete(jsonObject));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "uploadOther", method = RequestMethod.POST)
    public Result uploadOther(@RequestBody JSONObject jsonObject) {
        try {
            HttpSession session = SessionContext.getSession(jsonObject.getString("JSESSIONID"));
            ThrInOneEntity entity = new ThrInOneEntity();
            String creator = "";
            if (null != (entity = (ThrInOneEntity) session.getAttribute("currentUser"))) {
                creator = entity.getUuid();
            }
            String resourceId = jsonObject.getString("resourceId");
            JSONArray params = jsonObject.getJSONArray("params");
            for (int i = 0; i < params.size(); i++) {
                JSONObject param = params.getJSONObject(i);
                param.put("creator", entity.getUuid());
                param.put("resourceId", resourceId);
                param.put("sortId", "0");
                param.put("clazz", "Accessory");
                param.put("pageQuantity", "0");
                param.put("applicationName", "BG");
                param.put("programName", "BG");
                param.put("lastUploadTag", new Date());
                param.put("createDatetime", new Date());
                param.put("lastModifyTime", new Date());
                materialService.save(param);
            }
            return ResultGenerator.genSuccessResult("SUCCESS");
        } catch (Exception e) {
            return ResultGenerator.genFailResult("登陆失效");
        }
    }

    public String downloadOther(String id, String hbUrl) {
        String url = hbUrl + "/AppMessageCenter/HaveDoMatter/QueryMaterialDetail?materialId=" + id;
        HttpGet re = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = new DefaultHttpClient().execute(re);
        } catch (IOException e) {
            int i = 1;
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            try {
                String body = EntityUtils.toString(httpResponse.getEntity());
                JSONObject result = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(body).getString("Data")).getString("Response"));
                if ("成功".equals(result.getString("ResponseNote"))) {
                    return JSONObject.fromObject(result.getString("ResponseData")).getString("Material");
                }
                return "";
            } catch (IOException e) {
                int i = 1;
            }
        }
        return "";
    }
}
