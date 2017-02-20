package com.hisoka.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * RequestInfoFilter.java
 *
 * 打印获取请求的host, ip等
 *
 * @author: Hinsteny
 * @date: 2017年2月20日
 * @copyright: 2017 All rights reserved.
 * 
 */
public final class RequestInfoFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String UNKNOWN = "unknown";

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (logger.isDebugEnabled()){
            logger.debug("request from host: {}, addr:{} and ip is {}", request.getRemoteHost(), request.getRemoteAddr(), getRemoteIp(request));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy () {

    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     * @param req
     * @return
     */
    private String getRemoteIp (ServletRequest req) {
        String ip = null;
        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
            ip = request.getHeader("X-Forwarded-For");
            if (logger.isDebugEnabled()) {
                logger.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
            }

            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    if (logger.isDebugEnabled()) {
                        logger.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                    }
                }

                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    if (logger.isDebugEnabled()) {
                        logger.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                    }
                }

                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (logger.isDebugEnabled()) {
                        logger.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                    }
                }

                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    if (logger.isDebugEnabled()) {
                        logger.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                    }
                }

                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    if (logger.isDebugEnabled()) {
                        logger.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                    }
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0, count = ips.length; index < count; index++) {
                    String strIp = ips[index];
                    if (UNKNOWN.equalsIgnoreCase(strIp)) continue;
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
