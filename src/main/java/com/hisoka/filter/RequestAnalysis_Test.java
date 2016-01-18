package com.hisoka.filter;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * RequestAnalysis_Test.java
 *
 * @author: Hinsteny
 * @date: 2016年1月11日
 * @copyright: 2016 All rights reserved.
 *
 */
public class RequestAnalysis_Test implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestAnalysis.class);
    private static final String JSON_NAME = "apolication/json";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        @SuppressWarnings("rawtypes")
        Map parameterMap = req.getParameterMap();
        ObjectMapper mapper = new ObjectMapper();
        String jsonfromMap =  mapper.writeValueAsString(parameterMap);
//        parameterMap.forEach((k, v) ->{System.out.printl("key:{}  value:{}" k, v);});
        logger.info("Start request the URL {} and the request method is {}, take with params {} ***", new Object[]{req.getRequestURI(), req.getMethod(), jsonfromMap});
        long start = System.currentTimeMillis();
        chain.doFilter(request, responseWrapper);

        String type = responseWrapper.getContentType();
        if (type != null && type.contains(JSON_NAME)) {
            String responseContent = new String(responseWrapper.getDataStream());
            logger.info("response result type is: {}, and content is: {}", type, responseContent);
        }/*else {
            String responseContent = new String(responseWrapper.getDataStream());
            System.out.println("response result other content:" + responseContent.substring(0, 128));
        }*/
        byte[] responseToSend = responseWrapper.getDataStream();
        response.getOutputStream().write(responseToSend);
        long end = System.currentTimeMillis();
        logger.info("End request {} had finished with time(ms): {}", req.getRequestURI(), (end-start));
    }

    @Override
    public void destroy() {

    }

    /**
     * ResponseWrapper.java
     *
     * @author: Hinsteny
     * @date: 2016年1月11日
     * @copyright: 2016 All rights reserved.
     *
     */
    public class ResponseWrapper extends HttpServletResponseWrapper {

        ByteArrayOutputStream output;
        FilterOutputStream filterOutput;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
            output = new ByteArrayOutputStream();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (filterOutput == null) {
                filterOutput = new FilterOutputStream(output);
            }
            return filterOutput;
        }

        public byte[] getDataStream() {
            return output.toByteArray();
        }
    }

    /**
     * FilterOutputStream.java
     *
     * @author: Hinsteny
     * @date: 2016年1月11日
     * @copyright: 2016 All rights reserved.
     *
     */
    public class FilterOutputStream extends ServletOutputStream {

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public boolean isReady() {
            return true;
        }

        DataOutputStream output;
        public FilterOutputStream(OutputStream output) {
            this.output = new DataOutputStream(output);
        }

        @Override
        public void write(int arg0) throws IOException {
            output.write(arg0);
        }

        @Override
        public void write(byte[] arg0, int arg1, int arg2) throws IOException {
            output.write(arg0, arg1, arg2);
        }

        @Override
        public void write(byte[] arg0) throws IOException {
            output.write(arg0);
        }

    }

}
