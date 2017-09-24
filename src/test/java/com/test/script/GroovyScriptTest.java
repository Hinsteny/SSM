package com.test.script;

import com.hisoka.utils.GroovyCalculateUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 测试执行Groovy脚本
 * @author Hinsteny
 * @date 2017-09-22
 * @copyright: 2017 All rights reserved.
 */
public class GroovyScriptTest {

    @Test
    public void testGroovyNoResult(){
        String script = "def name='Hinsteny'; println name;";
        String result = GroovyCalculateUtil.calculate(script, null, String.class);
        System.out.println(result);
    }

    @Test
    public void testGroovyIntResult(){
        String script = "println \"$name age is $age\"; 1";
        Map<String, Object> param = new HashMap<>();
        param.put("name", "Hinsteny");
        param.put("age", "18");
        Integer result = GroovyCalculateUtil.calculate(script, param, Integer.class);
        System.out.println(result);
    }

    @Test
    public void testGroovyBooleanResult(){
        String script = "true";
        Boolean result = GroovyCalculateUtil.calculate(script, null, Boolean.class);
        System.out.println(result);
    }

    @Test
    public void testGroovyDoResult(){
        String script = "1.1";
        BigDecimal result = GroovyCalculateUtil.calculate(script, null, BigDecimal.class);
        System.out.println(result);
    }

    @Test
    public void testGroovyComplexScript () {
        Map<String, Object> param = new HashMap<>();
        param.put("merchant", "花旗书店");
        param.put("strategy", "1,2,3");
        param.put("book", new GroovyScriptTest.Book("hh", 5L));

        String script = "println \"Book shop name is $merchant, and it's strategy is $strategy \"; book.price > 10 ? true : false";
        Boolean result = GroovyCalculateUtil.calculate(script, param, Boolean.class);
        System.out.println(result);
    }

    private static class Book {
        private String name;
        private Long price;

        public Book(String name, Long price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }
    }
}
