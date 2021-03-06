package com.hisoka.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 * @author Hinsteny
 * @date 2015年8月11日
 */
public final class MapUtil {

    /**
     * 根据传入的数组生成一个HashMap,偶数下标表示key,奇数表示value.
     * @param params 如果传入的参数不是偶数个数，忽略最后一个。如果传入null,则直接返回一个空的map.
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map mapIt(Object...params){
        Map m = new HashMap();
        int length = params.length;
        if(params==null||length<2){
            return m;
        }else{
            if(length%2!=0){
                length--;//当传入的参数不是偶数个数，忽略最后一个
            }
            for(int i=0;i<length;){
                m.put(params[i++], params[i++]);
            }
        }
        return m;
    }

    /**
     * 把普通Java对象映射为json对象并返回json字符串
     * @param data
     * @return
     */
    public static String renderDataToStr(Map data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}
