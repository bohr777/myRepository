package com.owinfo.sql.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @auther qiyong
 * @create 2019-04-08 15:56
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/index")
public class LoginFilter implements Filter {

    @Value("indexUrl")
    private String indexUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        if (indexUrl.equals(url)) {
            chain.doFilter(servletRequest, servletResponse);
        }
        if (null == request.getSession().getAttribute("username")) {
            response.sendRedirect("http://localhost:8088/toLogin");
        }
    }

    @Override
    public void destroy() {

    }
}
