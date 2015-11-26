package com.hisoka.support.config;

import org.springframework.beans.BeansException;

/**
 * 配置注入出错时抛出的异常
 * 
 * @author Hinsteny
 * @date 2015-11-25
 */
public class ConfigAutowireException extends BeansException{

	private static final long serialVersionUID = -8396282668264775392L;

	public ConfigAutowireException(String msg) {
		super(msg);
	}

	public ConfigAutowireException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
