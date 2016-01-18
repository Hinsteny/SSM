package com.hisoka.utils;

/**
 * Object工具类
 * @author Hinsteny
 * @date 2015年8月11日
 */
public final class ObjectUtil {

	public static boolean isNull(Object o){
		return o==null;
	}
	
	public static boolean nonNull(Object o){
		return o!=null;
	}
	
	public static <T> T getDefault(T t,T defaultVal){
		if(t==null){
			return defaultVal;
		}
		return t;
	}
}
