package com.test.others;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Hinsteny
 * @date 2016/9/5
 * @copyright: 2016 All rights reserved.
 */
public class TestLinkHashMap {

    @Test
    public void searchLinkHashMap(){
        Map<String, String> map = new LinkedHashMap();
        map.put("b", "first");
        map.put("c", "second");
        map.put("a", "third");

        map.forEach((key, value) -> { System.out.println(key + " : " + value);});
    }

    @Test
    public void searchHashMap(){
        Map<String, String> map = new HashMap();
        map.put("b", "first");
        map.put("c", "second");
        map.put("a", "third");

        map.forEach((key, value) -> { System.out.println(key + " : " + value);});
    }

}
