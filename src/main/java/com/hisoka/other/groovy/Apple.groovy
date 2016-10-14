package com.hisoka.other.groovy

import org.junit.Assert;

/**
 * Created by Hinsteny on 2015/12/25.
 */
class Apple {
    String name;
    Integer age;
    String address;

    int method(String arg) {
        return 1
    }

    int method(Object arg) {
        return 2
    }

    void doTest(){
        Object o = "Object"
        int result = method(o)
        Assert.assertEquals(1, result)
    }

    void likeLambdas(){
        int[] array = [1, 2, 3]
        Runnable run = { println("run")}
        array.each {println(it)}
    }

    String toString(){
        return "name: " + name + " age: " + age + " address: " +address
    }

}
