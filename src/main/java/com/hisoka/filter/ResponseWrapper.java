package com.hisoka.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


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
    FilterServletOutputStream filterOutput;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new FilterServletOutputStream(output);
        }
        return filterOutput;
    }

    public byte[] getDataStream() {
        return output.toByteArray();
    }
}
