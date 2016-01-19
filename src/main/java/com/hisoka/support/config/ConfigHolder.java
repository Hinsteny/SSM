package com.hisoka.support.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置信息管理(snow.properties)
 * @author Hinsteny
 * @date 2015-11-25
 */
@Component
public final class ConfigHolder {

    private Logger Logger = LoggerFactory.getLogger(ConfigHolder.class);
    
    private static final String url = "snow.properties";
    
	private Properties properties = new Properties();
	
	public ConfigHolder(){
	    loadPropertiesFromFile();
	}
	
	public String get(String key){
		return properties.getProperty(key);
	}
	
	public String get(String key,String defaultValue){
		return properties.getProperty(key,defaultValue);
	}
	
	private void loadPropertiesFromFile(){
	    ClassPathResource resource = new ClassPathResource(url);
	    if(!resource.exists()){
	        Logger.info("The file of snow properties not exist under the classpath, so don't load properties！");
	        return;
	    }
	    try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
