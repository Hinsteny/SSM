package com.hisoka.filter;

import com.hisoka.other.SessionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 为客户端的每一个http请求线程设置自己的request和response对象
 *
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 *
 */
public class SessionFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		SessionContext.setRequest((HttpServletRequest) request);
		SessionContext.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
