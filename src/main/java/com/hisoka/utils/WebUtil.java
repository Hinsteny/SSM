package com.hisoka.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web 请求的一些通用方法
 *
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
public final class WebUtil {

    static Log log = LogFactory.getLog(WebUtil.class);

    public static final String DEFAULT_URL_CHARSET = "UTF-8";
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String SESSION_USER = "user";
    public static final String CLIENT_IP = "clientIP";

    /**
     * 是否为ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        log.debug("X-Requested-With: " + request.getHeader("X-Requested-With") + "   IP:"
                + getIpAddr(request));
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 保存数据到session
     * 
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value) {

    }

    /**
     * 获取客户端的IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String[] checkHeaders = new String[] {"X-Real-IP", /* nginx反向代理 */
        "X-Forwarded-For", /* 多层代理服务器 */
        "Proxy-Client-IP", "WL-Proxy-Client-IP"};

        String value;
        for (String header : checkHeaders) {
            if ((value = request.getHeader(header)) != null) {
                return value;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 支持微信5.0版本以上
     * 
     * @param request
     * @return
     */
    public static boolean isWechatRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("micromessenger")) {
                try {
                    Matcher m = Pattern.compile("micromessenger/([^\\.]+)\\.").matcher(userAgent);
                    if (m.find() && (Integer.valueOf(m.group(1)) >= 5)) {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isMobileRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("mobile")) {
                return true;
            }
        }
        // OperaMini special case
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            if (header.contains("OperaMini")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前请求的HOST
     * 
     * @return
     */
    public static String getWebHost() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        return "http://" + request.getHeader("host");
    }

    /**
     * 设置cookie
     * 
     * @param response HttpServletResponse
     * @param name 名称
     * @param value 值
     * @param maxAge 过期时间（秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie(基于浏览器会话)
     * 
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, -1);
    }

    /**
     * 删除cookie
     * 
     * @param response HttpServletResponse
     * @param name 名称
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        setCookie(response, name, null, 0);
    }

    public static String getRequsetUrl(HttpServletRequest request, boolean query) {
        StringBuilder sb = new StringBuilder(request.getRequestURL());
        String queryString = request.getQueryString();
        if (query && !Strings.isBlank(queryString)) {
            sb.append("?").append(queryString);
        }
        return sb.toString();
    }

    /**
     * 编码XSS攻击
     * 
     * @param value
     * @return
     */
    public static String escapeXSS(String value) {
        if (value == null || value.isEmpty())
            return value;
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\(.*\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "");
        value = value.replaceAll("script", "");
        return value;
    }
}
