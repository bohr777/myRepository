package com.owinfo.sql.util;

import java.io.*;

/**
 * @Description 图片转成16进制
 * @auther qiyong
 * @create 2018-11-30 10:12
 */
public class ImageToHex {

    public static void main(String[] args) throws Exception {
        try {
            StringBuffer sb = new StringBuffer();
            FileInputStream fis = new FileInputStream("D:\\xxxxx\\aaa.jpg");
            BufferedInputStream bis = new BufferedInputStream(fis);
            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            // 得到图片的字节数组
            byte[] result = bos.toByteArray();

            System.out.println(HexToImage.hexToImage(byte2HexStr(result).toString() + ";" + byte2HexStr(result).toString() + ";" + byte2HexStr(result).toString() + ";" + byte2HexStr(result).toString()));
//            String s = byte2HexStr(result).toString();
//            System.out.println("           "+s);
//             字节数组转成十六进制
//            String str = byte2HexStr(result);
//			/*
//			 * 将十六进制串保存到txt文件中
//			 */
//            PrintWriter pw = new PrintWriter(
//                    new FileWriter("D:\\xxxxx\\aaa.txt"));
//            pw.println(str);
//            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 实现字节数组向十六进制的转换方法一
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /*
     * 实现字节数组向十六进制的转换的方法二
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
