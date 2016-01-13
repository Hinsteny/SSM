package com.hisoka.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * RequestInterceptor.java
 * 
 * @author: Hinsteny
 * @date: 2016年1月12日
 * @copyright: 2016 All rights reserved.
 * 
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        @SuppressWarnings("rawtypes")
        Map parameterMap = req.getParameterMap();
        ObjectMapper mapper = new ObjectMapper();
        String jsonfromMap =  mapper.writeValueAsString(parameterMap);
        logger.info("Start request the URL {} and the request method is {}, take with params {} ***", new Object[]{req.getRequestURI(), req.getMethod(), jsonfromMap});
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Post-handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("After completion handle");
    }

}
