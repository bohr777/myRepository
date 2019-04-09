package com.owinfo.sql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @auther qiyong
 * @create 2019-04-08 16:24
 */
@Controller
public class CenterController {


    @RequestMapping("/index")
    public String index() {
        ModelAndView mav = new ModelAndView();
        return "index";
    }

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpSession session) {
        ModelAndView mav = new ModelAndView("index");
        session.setAttribute("username", "test");
        return mav;
    }
}
