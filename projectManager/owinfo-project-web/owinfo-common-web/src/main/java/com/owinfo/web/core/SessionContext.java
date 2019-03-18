package com.owinfo.web.core;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/18.
 */
public class SessionContext {
    public static HashMap<String,Object> sessionMap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) sessionMap.get(session_id);
    }
    public static synchronized boolean remove(String key){
        try{
            sessionMap.remove(key);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}