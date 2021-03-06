package com.hisoka.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 设置应用上下文,供如标签库使用
 * @author Hinsteny
 * @date 2015年8月11日
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		Assert.notNull(applicationContext, "applicationContext should not null");
		return applicationContext;
	}

	public static <T> T getBean(Class<T> c){
		return getApplicationContext().getBean(c);
	}

    /**
     * 获取web真实路径，此方法在applicationcontext初始化完后即可调用.
     * @param applicationContext
     * @return
     */
    public static String getWebAppPath(ApplicationContext applicationContext){
        try{
            WebApplicationContext context = (WebApplicationContext) applicationContext;
            return URLDecoder.decode(context.getServletContext().getRealPath("/"), "UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return "";
        }
    }
}
