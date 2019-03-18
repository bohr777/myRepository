package com.owinfo.configurer.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginFilter extends AbstractFilter{
	
	 @Override
	 public void doFilter(ServletRequest req, ServletResponse resp,
                          FilterChain chain) throws IOException, ServletException {
	         
	       HttpServletRequest request = (HttpServletRequest) req;
	       HttpServletResponse response = (HttpServletResponse) resp;
         
	       
	        //判断如果是index.jspx 放行  
	       String uri = request.getRequestURI();
	       uri = uri.substring(uri.lastIndexOf("/")+1);
	        
	         if("".equals(uri) || uri.endsWith("htm")|| uri.endsWith("js")|| uri.endsWith("jpg")|| uri.endsWith("do")  || "login.jsp".equals(uri) || "index.jsp".equals(uri)|| uri.endsWith("png") || uri.endsWith("css")) {  
	            //所有人都能请求到的URI，放行  
	          chain.doFilter(request, response);
	          return;
	       } else {    //下面是判断是否有session，也就是用户是否已登录状态；                                                                                                                                          
	             HttpSession session = request.getSession();
	            String user =  (String) session.getAttribute("usercode");
	         if(user == null) {
	               String remoteAddr = request.getContextPath();
	               String remoteHost = request.getLocalAddr();
	               int remotePort = request.getLocalPort();
	               double random = Math.random();
	               String addr ="http://"+remoteHost+":"+remotePort+remoteAddr;

	            	   response.sendRedirect(addr);
	        
	            } else {  
	                chain.doFilter(request, response);  
	                return;
	            }  
	        }     
	          
	    }  

}

	
