package com.hisoka.filter;

import com.hisoka.Component.WeChatManager;
import com.hisoka.support.weChat.WeChatPageUtil;
import com.hisoka.utils.Strings;
import com.hisoka.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 拦截微信请求获取OPENID
 *
 * WechatFilter
 *
 * @author Hinsteny
 * @date 2015-8-25
 * @copyright: 2015 成都二十三魔方生物科技有限公司 All rights reserved.
 */
public class WechatFilter implements Filter {

    private static final Logger logger  = LoggerFactory.getLogger(WechatFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (response.isCommitted()) {
            logger.info("The current request has been submitted.");
            chain.doFilter(request, response);
            return;
        }

        if (!WebUtil.isWechatRequest(httpRequest)) {
            logger.info("The current request is not from wechat client.");
            chain.doFilter(request, response);
            return;
        }
        String requestURL = WebUtil.getRequsetUrl(httpRequest, true);
        System.out.println("Request from wechat client：" + requestURL);
        logger.info("Request from wechat client：{}", requestURL);
        HttpSession session = httpRequest.getSession();
        String weChatOpenId = (String) session.getAttribute(WeChatManager.WECHATOPENID);
        if (weChatOpenId == null) {
            if (httpRequest.getParameter(WeChatManager.CODE) == null) {
                logger.info("Request from wechat,and send to ask oauth_code, session_id:[{}] for url: {}", session.getId(), requestURL);
                String url = WeChatManager.getWechatUserAuthorizeSilentUrl(requestURL);
                response.reset();
                System.out.println("Reset the request and send to : {} for oauth_code" + url);
                logger.info("Reset the request and send to : {} for oauth_code", url);
                ((HttpServletResponse) response).sendRedirect(url.toString());
                return;
            } else {
                String oauthCode = httpRequest.getParameter(WeChatManager.CODE);
                if (!Strings.isBlank(oauthCode)) {
                    Map<String, Object> oauth_data = WeChatPageUtil.getWechatPageAccessToken(oauthCode);
                    weChatOpenId = oauthCode != null && oauth_data.get(WeChatManager.OPENID) != null ? (String) oauth_data.get(WeChatManager.OPENID) : null;
                    session.setAttribute(WeChatManager.WECHATOPENID, weChatOpenId);
                } else {
                    logger.warn("The oauth_code is empty!");
                }
            }
        }

        logger.info("URL[{}], SESSION[{}], OPENID[{}]", requestURL, session.getId(), weChatOpenId);
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }

}
