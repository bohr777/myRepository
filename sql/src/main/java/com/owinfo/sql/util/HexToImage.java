package com.owinfo.sql.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description 16进制转图片
 * @auther qiyong
 * @create 2018-11-30 10:07
 */
public class HexToImage {


    public static String hexToImage(String hexs) {
        String[] hex1=hexs.split(";");
        StringBuilder sb=new StringBuilder();
        for (String hex:hex1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new Date());
            System.out.println("today    " + today);
            File file = new File("C:\\\\kk\\\\" + today);
            if (!file.exists() && !file.isDirectory()) {
//                System.out.println("目录不存在");
                file.mkdirs();
            }
//            else {
//                System.out.println("目录存在");
//            }
            String pan = "C:\\\\";
            String path = "kk\\\\" + today+ "\\\\"+UUID.randomUUID().toString()+".jpg";

            sb.append(path+";");
            HexToImage.saveToImgFile(hex.toString().toUpperCase(), pan+path);
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }


    public static void main(String[] args) throws Exception {
//        C:\\kk\\
        HexToImage to = new HexToImage();
        InputStream is = new FileInputStream("D:\\xxxxx\\aaa.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String str = null;
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            sb.append(str);
        }
        to.saveToImgFile(sb.toString().toUpperCase(), "D:\\xxxxx\\aaa.jpg");

    }


    public static String saveToImgFile(String src, String output) {
        if (src == null || src.length() == 0) {
            return "error";
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            byte[] bytes = src.getBytes();
            for (int i = 0; i < bytes.length; i += 2) {
                out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
            }
            out.close();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private static int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }


}
