package com.hisoka.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;

/**
 * @author Hinsteny
 * @date 2017-09-22
 * @copyright: 2017 All rights reserved.
 */
public class GroovyCalculateUtil {

    /**
     * 执行一段groovy脚本并返回值
     * @param script
     * @param param
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T calculate(String script, Map<String, Object> param, Class<T> value) {
        Binding binding = new Binding(param);
        GroovyShell shell = new GroovyShell(binding);
        Object result = shell.evaluate(script);
        return (T) result;
    }
}
