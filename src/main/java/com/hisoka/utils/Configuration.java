package com.hisoka.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : Hinsteny
 * @date : 2016/09/20
 * @copyright All rights
 */
public class Configuration {

    private static Logger logger = LoggerFactory.getLogger(Configuration.class);

    /* default properties value*/
    private static final String DEFAULT_VALUE = null;

    /* The app config file under class path*/
    private static final String CONFIG_FILE = "config/App.properties";
    /* If configuration init succeed*/
    private static boolean init = false;
    /* Configuraction Object */
    private static volatile Configuration config = null;
    /* Properties Object */
    private static Properties props = null;

    private boolean init() throws IOException{
    	Resource resource = new ClassPathResource(CONFIG_FILE);
        props = PropertiesLoaderUtils.loadProperties(resource);
        if (props == null) {
            throw new IOException("Config file "+CONFIG_FILE+" not found");
        }else {
            if (logger.isInfoEnabled()) logger.info("Config file "+CONFIG_FILE+" load succeed");
            if (logger.isInfoEnabled()) logger.info("props: {}", props.entrySet().toString());
        }
        return true;
    }
    
    private Configuration() {
        try {
            init = init();
        } catch (IOException e) {
            if (logger.isInfoEnabled()) logger.info("Inital Configuration failed for {}", e.toString());
        }
    }
    
    public static Configuration getInstance() {
        if (config == null) {
            synchronized (Configuration.class) {
                if (config == null) {
                    config = new Configuration();
                }
            }
        }

        return (config);
    }

    public String getValue(String key) {
        return init ? props.getProperty(key) : DEFAULT_VALUE;
    }

    public Boolean getValueAsBoolean(String key) {
    	String value = getValue(key);
        return StringUtil.isBlank(value) ? null : Boolean.valueOf(value);
    }

    public static void main(String[] args){
    	Configuration instance = Configuration.getInstance();
    	System.out.println(instance.getValue("app_name"));
    }
}
