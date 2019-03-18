//package com.owinfo.core;
//
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.UUID;
//
///**
// * Created by Administrator on 2017/12/13.
// */
//public class FileHandleUtil {
//    public static String upload(MultipartFile file,String next) throws IOException {
//        try{
//            String name=file.getOriginalFilename();
//            String finalName= UUID.randomUUID().toString();
//            if(file.getBytes().length>0){
//                File finalFile=new File("D:/BG/"+finalName+"."+next);
//                OutputStreamWriter osw=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(finalFile)));
//                String temp=new String(file.getBytes());
//                osw.write(temp);
//                osw.flush();
//                osw.close();
//                return finalName;
//            }
//        }catch(Exception e){
//                int i=1;
//        }
//        return null;
//    }
//    public static ResponseEntity<InputStreamResource> download(String fullPath)
//            throws IOException {
//        String filePath = fullPath;
//        FileSystemResource file = new FileSystemResource(filePath);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(file.contentLength())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new InputStreamResource(file.getInputStream()));
//    }
//}
