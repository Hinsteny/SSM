package com.test.others.loading;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/1/20
 * @copyright: 2016 All rights reserved.
 */
public class BeLoad {

    public void test_1(){
        // 不会加载父类
//        SuperInter superO = new Child();
//        test_2((Parent)superO);
        Object objectO = new Child();
        test_2((Parent) objectO);
//
//        // 会加载父类
//        Child child = new Child();
//        test_2(child);
    }

    public void test_2(Parent object){
        System.out.println(object);
    }

}
