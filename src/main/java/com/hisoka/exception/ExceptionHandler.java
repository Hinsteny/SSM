package com.hisoka.exception;

import com.hisoka.result.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统异常处理,主要用于日志的记录
 *
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("error",ex);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> resultClass = handlerMethod.getReturnType().getParameterType();
            if (resultClass == WebResponse.class) {
                response.setContentType("text/plain; charset=UTF-8");
                try {
                    response.getWriter().write(WebResponse.fail(ex.getClass().getName() + ":" + ex.getMessage()).toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return new ModelAndView("pages/accessError", "errorMsg", ex.getLocalizedMessage());
            }
        }
        return null;
    }
}
