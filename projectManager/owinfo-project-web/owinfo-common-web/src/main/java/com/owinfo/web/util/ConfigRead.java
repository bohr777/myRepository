package com.owinfo.web.util;

import java.util.Map;

public class ConfigRead {
    /**
     * 系统文件配置 加载。
     */
    //public static Map<String, String> PUBLIC_SYSTEM = ResourcesUtils.getResource("/conf/info.properties").getMap();
//    public static String configPath;
    public static Map<String, String> PUBLIC_SYSTEM =ResourcesUtils.getResource("/services.properties").getMap();

    //public static Map<String, String> PUBLIC_SYSTEM = ResourcesUtils.getResource("conf/info.properties").getMap();
//    static {
//        configPath = ConfigRead.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//
//        try {
//            configPath = URLDecoder.decode(configPath, "UTF-8");
//        } catch (Exception var3) {
//        }
//
//        String var1 = (new File(configPath)).getParentFile().getAbsolutePath();
//        configPath = var1.split("file:")[0] + "\\services.properties";
//        PUBLIC_SYSTEM;
//    }


    /**
     * flx login page
     */
    public final static String TASK_URL = PUBLIC_SYSTEM.get("taskurl");


}
