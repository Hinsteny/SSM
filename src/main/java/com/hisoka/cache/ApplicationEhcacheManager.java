package com.hisoka.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.config.CacheConfigurationBuilder;
import org.ehcache.spi.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;


/**
 * CacheManager.java
 * 
 *  ***出问题了，暂停处理***
 * @author: Hinsteny
 * @date: 2015年11月24日
 * @copyright: 2015 All rights reserved.
 * 
 */
@Service
public class ApplicationEhcacheManager {

    /*Logger logger = LoggerFactory.getLogger(ApplicationEhcacheManager.class);
    
    private volatile CacheManager cacheManager;
    private volatile Cache<? extends Serializable, ? extends Serializable> defaultCache;
    private ConcurrentHashMap<Object, Cache<? extends Serializable, ? extends Serializable>> cacheMap = new ConcurrentHashMap<Object, Cache<? extends Serializable, ? extends Serializable>>();
    
    *//**
     * Just put one value and key into a cache by cacheName
     * @param key
     * @param value
     * @param cacheName
     * @return
     *//*
    public boolean setValue(Object key, Class<? extends Serializable> value, String cacheName){
        Cache<? extends Serializable, ? extends Serializable> targetCache = defaultCache;
        if(!Strings.isNullOrEmpty(cacheName)){
            targetCache = cacheMap.get(cacheName);
            if(targetCache == null){
                logger.warn("The target cache {0} that want to put in is not exist!", cacheName);
                //Here will create a new Cache object by cacheName, then could continue to put value (need to do...)
                return false;
            }
        }
//        setValueIntoCache(key, value, targetCache);
        return true;
    }
    
    *//**
     *  Get a value object by key from cache identified by cacheName
     * @param key
     * @param value
     * @param cacheName
     * @return
     *//*
    public Object getValue(String key, Object value, String cacheName){
        Cache<? extends Serializable, ? extends Serializable> targetCache = defaultCache;
        if(!Strings.isNullOrEmpty(cacheName)){
            targetCache = cacheMap.get(cacheName);
            if(targetCache == null){
                logger.warn("The target cache {0} that want to put in is not exist!", cacheName);
                return false;
            }
        }
        return getValueFromCache(key, targetCache);
    }
    
    private boolean setValueIntoCache(Object key, Class<? extends Serializable> value, Cache<Object, Object> cache){
        cache.put(key, new String());
        return true;
    }
    
    private Object getValueFromCache(Object key, Cache<? extends Serializable, ? extends Serializable> cache){
        return cache.get(key);
    }
    
    @PostConstruct
    public void init(){
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()    //Create a new {@link CacheConfigurationBuilder} instance;
                .withCache("defaultCache",
                    CacheConfigurationBuilder.newCacheConfigurationBuilder()
                        .buildConfig(Serializable.class, Serializable.class)) //
                .build(true);
        
        logger.debug("CacheManager of ehcache has been created and start servicing");
        
        defaultCache = cacheManager.getCache("defaultCache", Serializable.class, Serializable.class);
        cacheMap.put("defaultCache", defaultCache);
        
        //Do cache test
        testCache();
    } 
    
    private void testCache(){
        Cache<Object, ? extends Serializable> defaultCache = cacheMap.get("defaultCache");
        cacheMap.remove("defaultCache");
//        defaultCache.put("test", new String("Test defaultCache!"));
        logger.debug("get the defaultCache test value!", defaultCache.get("test"));
        
        Cache<Long, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
                .newCacheConfigurationBuilder().buildConfig(Long.class, String.class));
        myCache.put(1L, "da one!"); 
        logger.debug("get the myCache test value!", myCache.get(1L));
    }
    
    @PreDestroy
    public void destroy(){
        cacheManager.close(); 
    }
    */
}
