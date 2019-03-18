package com.owinfo.web.util;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
public class FileHandleUtil {
    public static String upload(MultipartFile file, String next, String finalPath) throws IOException {
        try {
            if(file.getBytes().length>0){
                String name = file.getOriginalFilename();
                String finalName = UUID.randomUUID().toString();
                finalPath = finalPath.endsWith("/") ? finalPath : finalPath+"/";
                if (finalPath.startsWith("smb")) {
                    SmbFile finalFile = new SmbFile(finalPath + "/" + finalName + "." + next);
                    finalFile.connect();
                    BufferedOutputStream osw = new BufferedOutputStream(new SmbFileOutputStream(finalFile));
                    byte[] b = file.getBytes();
                    osw.write(b);
                    osw.flush();
                    osw.close();
                    return finalPath + "/" + finalName + "." + next;
                } else {
                    File dir = new File(finalPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    if (file.getBytes().length > 0) {
                        File finalFile = new File(finalPath + finalName + "." + next);
                        BufferedOutputStream osw = new BufferedOutputStream(new FileOutputStream(finalFile));
                        byte[] b = file.getBytes();
                        osw.write(b);
                        osw.flush();
                        osw.close();
                        return finalPath + finalName + "." + next;
                    }
                }
            }else{
                return "0";
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static ResponseEntity<InputStreamResource> download(String fullPath)
            throws IOException {
        String filePath = fullPath;
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream; charset=utf-8"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
