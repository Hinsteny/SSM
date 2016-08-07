package com.hisoka.handler;

import com.hisoka.rest.Delete;
import com.hisoka.rest.Get;
import com.hisoka.rest.Post;
import com.hisoka.rest.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Web rest 注解的处理器
 *
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 */
public class RestAnnotationHandler extends RequestMappingHandlerMapping {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 在配置处理静态资源default-servlet-handler时, spring默认会有四个HandlerMapping, 加上这里定义的这个会有五个,为了让自定义注解优先被选中, 设置排序最大
	 */
	private int order = -1;

	public RestAnnotationHandler() {
		super();
		this.setOrder(order);
	}

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;
		
		List<RequestMethod> requestMethod = new ArrayList<>(RequestMethod.values().length);
		String[] value = null;
		if(method.isAnnotationPresent(Post.class)){
			Post postAnnotation = AnnotationUtils.findAnnotation(method, Post.class);
			requestMethod.add(RequestMethod.POST);
			value = postAnnotation.value();
		}else if(method.isAnnotationPresent(Get.class)){
			Get getAnnotation = AnnotationUtils.findAnnotation(method, Get.class);
			requestMethod.add(RequestMethod.GET);
			value = getAnnotation.value();
		}else if(method.isAnnotationPresent(Put.class)){
			Put putAnnotation = AnnotationUtils.findAnnotation(method, Put.class);
			requestMethod.add(RequestMethod.PUT);
			value = putAnnotation.value();
		}else if(method.isAnnotationPresent(Delete.class)){
			Delete deleteAnnotation = AnnotationUtils.findAnnotation(method, Delete.class);
			requestMethod.add(RequestMethod.DELETE);
			value = deleteAnnotation.value();
		}else if(method.isAnnotationPresent(RequestMapping.class)){
			RequestMapping annotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
			requestMethod.addAll(Arrays.asList((annotation.method()!=null && annotation.method().length>0)?annotation.method():RequestMethod.values()));
			value = annotation.value();
		}
		
		final String[] requestMappingValue = value;
		if(requestMethod.size() > 0){
			final RequestMethod[] requestMappingRequestMethod = requestMethod.toArray(new RequestMethod[requestMethod.size()]);
			logger.info("Open a web rest annotation: {} and method {}!", requestMappingValue, requestMappingRequestMethod.toString());
			RequestMapping methodAnnotation  = new RequestMapping() {
				@Override
				public Class<? extends Annotation> annotationType() {
					return RequestMapping.class;
				}

                @Override
                public String name() {
                    return "";
                }

                @Override
				public String[] value() {
					return requestMappingValue;
				}
				
				@Override
				public String[] produces() {
					return new String[]{};
				}
				
				@Override
				public String[] params() {
					return new String[]{};
				}
				
				@Override
				public RequestMethod[] method() {
					return requestMappingRequestMethod;
				}
				
				@Override
				public String[] headers() {
					return new String[]{};
				}
				
				@Override
				public String[] consumes() {
					return new String[]{};
				}

                @Override
                public String[] path() {
                    return requestMappingValue;
                }
			};
			
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			info = createRequestMappingInfo(methodAnnotation, methodCondition);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				info = createRequestMappingInfo(typeAnnotation, typeCondition).combine(info);
			}
		}
		return info;
	}
	
}
