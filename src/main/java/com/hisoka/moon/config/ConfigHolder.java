package com.hisoka.moon.config;

import org.springframework.stereotype.Component;
import com.hisoka.utils.PropertiesUtils;
import java.util.Properties;

/**
 * 配置信息管理(moon.properties)
 * @author Gavin
 * @date 2014-4-29
 */
@Component
public final class ConfigHolder {

	private Properties properties;
	
	public ConfigHolder(){
		this.properties = PropertiesUtils.loadPropertiesFileIfExist("moon.properties");
	}
	
	public String get(String key){
		return properties.getProperty(key);
	}
	
	public String get(String key,String defaultValue){
		return properties.getProperty(key,defaultValue);
	}
}
