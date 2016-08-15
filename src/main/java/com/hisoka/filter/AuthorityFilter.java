package com.hisoka.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

/**
 * AuthorityFilter.java
 *
 * 用作系统的访问权限控制拦截器
 *
 * @author: Hinsteny
 * @date: 2016年4月6日
 * @copyright: 2016 All rights reserved.
 * 
 */
public class AuthorityFilter implements Filter {

    private String SIGN = "sign";

    public static final String PERMISSION = "permissions";

    private String LOGINURL = "/index.html";
    private String[] PASSURL = {"/user/loginout.html"};
    private List<String> PASSURLLIST;

    private static final Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

    private volatile boolean hasInit = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (!hasInit){

        }
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        HttpServletResponse httpResponse = (HttpServletResponse)response;
//        String reqUrl = WebUtil.getRequsetPath(httpRequest, false);
//        if (!(PASSURLLIST.contains(reqUrl) || reqUrl.equals(LOGINURL))){
//            RequestDispatcher dispatcher = request.getRequestDispatcher(LOGINURL);
//            HttpSession session = httpRequest.getSession(true);
//            List<String> permissions;
//            permissions = (permissions = (List<String>) session.getAttribute(PERMISSION)) == null ? new ArrayList<String>() : permissions;
//            if (permissions == null || permissions.size() == 0){
//                Long userId = getUserIdFromRequest(httpRequest);
//                if (logger.isInfoEnabled()) logger.debug("UserId: {}", userId);
//                session.setAttribute(PERMISSION, permissions);
//            }
//            if (!permissions.contains(reqUrl)){
//                dispatcher.forward(request, response);
//                return;
//            }
//        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
