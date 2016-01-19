package com.hisoka.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * 配置文件读取
 * @author Hinsteny
 * @date 2015年8月11日
 */
public final class PropertiesUtils {

	private static final Logger	logger		= LoggerFactory.getLogger(PropertiesUtils.class);
    
	public static Properties loadPropertiesFile(String filePath) throws FileNotFoundException, IOException{
		Properties p = new Properties();
		if(!filePath.startsWith("/")){
			filePath="/"+filePath;
		}
		URL url = PropertiesUtils.class.getResource(filePath);
		if(url == null){
			throw new FileNotFoundException(url+" can not found.");
		}
      
		InputStream in = PropertiesUtils.class.getResourceAsStream(filePath);
        p.load(in);
		return p;
	}
	
	public static Properties loadPropertiesFileIfExist(String filePath){
		try {
			return loadPropertiesFile(filePath);
		} catch (FileNotFoundException e) {
			logger.warn("文件"+filePath+"不存在,不进行加载.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Properties();
	}
}
