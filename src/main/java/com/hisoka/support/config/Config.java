package com.hisoka.support.config;

import java.lang.annotation.*;

/**
 * 用于注入资源文件的配置值,value为配置的名称
 * 
 * @author Hinsteny
 * @date 2015-11-25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Config {

	String value();
	
	String defaultVal() default "";
	
}
