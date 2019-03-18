package com.owinfo.web.util;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourcesUtils {
    private static ResourceBundle resourceBundle;

	/*private ResourcesUtils(String resource) {
		resourceBundle = ResourceBundle.getBundle(resource);
	}
	*/



    private ResourcesUtils(String filepath) {
        BufferedInputStream inputStream;
       // String filename = filepath;
        String filename２ = System.getProperty("java.class.path");
        String sysName = "/searchCreateIndex";
        String filename = System.getProperty("user.dir").replace(sysName,"")+"/"+ filepath;
        //String filename = filepath;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filename));
//            InputStream in = this.getClass().getResourceAsStream(filepath);
            resourceBundle = new PropertyResourceBundle(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            return ;
        } catch (IOException e) {
            return ;
        }
    }

    public static ResourcesUtils getResource(String filepath) {
        return new ResourcesUtils(filepath);
    }


    /**
     * 获取资源
     * @param resource 资源
     * @return 解析
     *//*
	public static ResourcesUtils getResource(String resource) {
		return new ResourcesUtils(resource);
	}*/

    /**
     * 根据key取得value
     *
     * @param key  键值
     * @param args value中参数序列，参数:{0},{1}...,{n}
     * @return
     */
    public String getValue(String key, Object... args) {
        String temp = resourceBundle.getString(key);
        return MessageFormat.format(temp, args);
    }

    /**
     * 获取所有资源的Map表示
     *
     * @return 资源Map
     */
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : resourceBundle.keySet()) {
            map.put(key, resourceBundle.getString(key));
        }
        return map;
    }
}
