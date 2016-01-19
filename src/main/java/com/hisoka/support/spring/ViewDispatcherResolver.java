package com.hisoka.support.spring;

import com.hisoka.utils.WebUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * MailSender.java
 *
 * @author: Hinsteny
 * @date: 2016年1月20日
 * @copyright: 2016 All rights reserved.
 *
 */
@Data
public class ViewDispatcherResolver extends UrlBasedViewResolver {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String webPrefix = "";

    private String webSuffix = "";

    private String mobilePrefix = "";

    private String mobileSuffix = "";

    private boolean enabled = false;

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return super.resolveViewName(getDeviceViewName(viewName), locale);
    }

    protected String getDeviceViewName(String viewName) {
        if (viewName.startsWith(REDIRECT_URL_PREFIX) || viewName.startsWith(FORWARD_URL_PREFIX)) {
            return viewName;
        }
        return getDeviceViewNameInternal(viewName);
    }

    protected String getDeviceViewNameInternal(String viewName) {
        if (!enabled)
            return getWebResolvedViewName(viewName);

        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        String resolvedViewName;
        if (WebUtil.isMobileRequest(request)) {
            resolvedViewName = getMobileResolvedViewName(viewName);
        } else {
            resolvedViewName = getWebResolvedViewName(viewName);
        }

        logger.trace("[{}] dispatcher view to:[{}]", request.getRequestURI(), resolvedViewName);
        return stripTrailingSlash(resolvedViewName);
    }

    /**
     * 定位试图到web view 目录
     *
     * @param viewName
     * @return
     */
    private String getWebResolvedViewName(String viewName) {
        return getWebPrefix() + viewName + getWebSuffix();
    }

    /**
     * 定位试图到mobile view 目录
     *
     * @param viewName
     * @return
     */
    private String getMobileResolvedViewName(String viewName) {
        return getMobilePrefix() + viewName + getMobileSuffix();
    }

    private String stripTrailingSlash(String viewName) {
        if (viewName.endsWith("//")) {
            return viewName.substring(0, viewName.length() - 1);
        }
        return viewName;
    }
}
