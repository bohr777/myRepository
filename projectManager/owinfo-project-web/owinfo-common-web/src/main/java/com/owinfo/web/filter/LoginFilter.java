package com.owinfo.web.filter;

import cn.gov.customs.casp.sdk.h4a.sso.passport.PassportFilter;
import cn.gov.customs.casp.sdk.h4a.sso.passport.util.CommonHelper;
import cn.gov.customs.casp.sdk.h4a.sso.passport.util.PassportManager;
import com.owinfo.web.config.util.PassportSSO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/12/16.
 */
@Order(2)
@WebFilter(filterName = "LoginFilter", urlPatterns = "/taskbook/checkLogin")
public class LoginFilter extends PassportFilter {

    @Value("${loginAddress}")
    private String loginAddress;

    public LoginFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String type=request.getParameter("type");
        if(null!=type&&"logout".equals(type)){
            new PassportSSO().LogOut(request,response);
            String logout=PassportManager.getLogoutUrl(request).replaceAll("8460/index","8089/owinfoProject/taskbook/checkLogin");
            response.sendRedirect(logout);
            return;
        }
        if (null == request.getSession().getAttribute("currentUser")) {
            String requestUri = request.getRequestURI();
            String requestUrl = request.getRequestURL().toString();
//            String[] rus=requestUrl.split(port);
            requestUrl=loginAddress;
            String regex = "<>;{};\'\"\n\r ";
            requestUri = requestUri.replaceAll("[" + regex + "]*", "");
            requestUrl = requestUrl.replaceAll("[" + regex + "]*", "");
            String path = request.getContextPath();
            String basePath = request.getScheme().replaceAll("[" + regex + "]*", "") + "://" + request.getServerName() + ":" + request.getServerPort() + path;
            if (requestUri != null && requestUrl.equals(path + logOffCallBackUrl)) {
                PassportManager.logout(request, response);
            } else if (CommonHelper.urlRoleCheck(requestUri.substring(path.length()), anonymousAuthentication)) {
                arg2.doFilter(servletRequest, servletResponse);
            } else {
                String jumptourl;
                String fc;
                if (request.getParameter(paramT) != null) {
                    jumptourl = request.getParameter(paramT);
                    fc = CommonHelper.decryptTicket(jumptourl);
                    try {
                        PassportManager.login(fc, request, response, slidingTime);
                    } catch (Exception e) {
                        int i=1;
                    }
                    arg2.doFilter(servletRequest, servletResponse);
                } else if (!PassportManager.isLogin(request, response)) {
                    jumptourl = authenticateUrl;
                    fc = "False";
                    String defaultMode = daMode;
                    try {
                        if ((new Integer(slidingTime)).intValue() != 0) {
                            fc = "True";
                        }
                        String mode = CommonHelper.urlRoleCheck(requestUri.substring(path.length()));
                        if (mode != null) {
                            defaultMode = mode;
                        }
                    } catch (Exception e) {
                        int i=1;
                    }

                    jumptourl = authenticateUrl + "?aid=" + appId + "&pm=" + defaultMode + "&apk=" + CommonHelper.getUrlPubKey() + "&t=" + paramT + "&lou=" + URLEncoder.encode(basePath + logOffCallBackUrl, "utf-8") + "&ip=" + request.getRemoteAddr().replaceAll("[" + regex + "]*", "") + "&fciam=False" + "&iam=" + idMode + "&ru=" + URLEncoder.encode(requestUrl, "utf-8") + "&fc=" + fc + "&sid=" + uuid;
                    response.sendRedirect(jumptourl);
                    return;
                } else {
                    arg2.doFilter(servletRequest, servletResponse);
                }

            }
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }
}
