package com.test.reflect.aspectJ;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/23
 * @copyright: 2016 All rights reserved.
 */
public class Apple {

    private volatile Integer count = 0;

    public int count(){

        //返回apple的数量
        return 10;
    }

    public void add(int amount){
        count+=amount;
    }

    public static void main(String[] args){
        Apple apple = new Apple();
        System.out.println(apple.count);
        apple.add(10);
        System.out.println(apple.count);
    }
}
