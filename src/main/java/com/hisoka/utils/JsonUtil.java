package com.hisoka.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hinsteny.bean.socket.Greeting;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/16
 * @copyright: 2016 All rights reserved.
 */
public class JsonUtil {

    private static JsonGenerator jsonGenerator = null;

    private static ObjectMapper objectMapper = null;

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static String writeEntityJSON(Object object){
        String str = null;
        ObjectMapper objectMapper = getObjectMapper();
        try {
            str = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
        return str;
    }

    public static void main(String[] args){
        // Test for change Object to jsonstr
        Map<String, Object> data1 = new HashMap<>();
        data1.put("key", "value");
        Test test = new Test("hello");
        data1.put("greeting", test);
        System.out.println(JsonUtil.writeEntityJSON(data1));
        List<Test> data2 = new ArrayList<>();
        data2.add(test);
        System.out.println(JsonUtil.writeEntityJSON(data2));
        data1.put("list", data2);
        System.out.println(JsonUtil.writeEntityJSON(data1));

        // Test for change Object to jsonstr

    }

    public static class Test {

        private String content;
        private String time;

        public Test() {

        }

        public Test(String content) {
            this.content = content;
            this.time = LocalDateTime.now().toString();
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

}
