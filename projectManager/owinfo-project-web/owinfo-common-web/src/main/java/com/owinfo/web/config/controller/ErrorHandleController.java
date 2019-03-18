package com.owinfo.web.config.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liyue on 2017/10/19.
 *
 *   错误信息
 */
@Controller
public class ErrorHandleController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String errorHandle(){
        return "500";
    }
}
