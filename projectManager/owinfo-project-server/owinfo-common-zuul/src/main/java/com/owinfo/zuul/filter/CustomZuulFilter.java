package com.owinfo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liyue on 2017/9/25.
 */
public class CustomZuulFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomZuulFilter.class);

    /**
     *  pre：    路由之前
     *  routing：路由之时
     *  post：   路由之后
     *  error：  错误时
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否需要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 逻辑
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response=ctx.getResponse();
        logger.info("zuul url ---------->" + request.getRequestURL().toString());

        return null;
    }
}
