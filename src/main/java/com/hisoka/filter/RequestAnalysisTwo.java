package com.hisoka.filter;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * RequestAnalysis_Test.java
 * 
 * @author: Hinsteny
 * @date: 2016年1月11日
 * @copyright: 2016 All rights reserved.
 * 
 */
public class RequestAnalysisTwo implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestAnalysisTwo.class);
    private static final String JSON_NAME = "json";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        @SuppressWarnings("rawtypes")
        Map parameterMap = req.getParameterMap();
        ObjectMapper mapper = new ObjectMapper();
        String jsonfromMap =  mapper.writeValueAsString(parameterMap);
//        parameterMap.forEach((k, v) ->{System.out.printl("key:{}  value:{}" k, v);});
        logger.info("Start request the URL {} and the request method is {}, take with params {} ***", new Object[]{req.getRequestURI(), req.getMethod(), jsonfromMap});
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);

        System.out.println("response result type:" + response.getContentType());
        String type = response.getContentType();
        if (type != null && type.contains(JSON_NAME)) {
            byte[] bytes = new byte[1024];
            response.getOutputStream().write(new String("test").getBytes());
            String responseContent = new String(bytes);
            System.out.println("response result json content:" + responseContent);
        }else {
            byte[] bytes = new byte[response.getBufferSize()];
            response.getOutputStream().write(bytes);
            String responseContent = new String(bytes);
            System.out.println("response result other content:" + responseContent.substring(0, 128));
        }
        long end = System.currentTimeMillis();
        logger.info("End request {} had finished with time(ms): {}", req.getRequestURI(), (end-start));
    }

    @Override
    public void destroy() {
        
    }

}
