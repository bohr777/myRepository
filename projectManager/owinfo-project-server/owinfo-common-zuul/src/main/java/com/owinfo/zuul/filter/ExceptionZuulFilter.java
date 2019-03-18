package com.owinfo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liyue on 2017/9/25.
 */
public class ExceptionZuulFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionZuulFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("zuul error ---------->" + request.getRequestURL().toString());
        return null;
    }
}
