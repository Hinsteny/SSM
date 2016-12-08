package com.test.reflect;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/2
 * @copyright: 2016 All rights reserved.
 */
public interface Scholar {

    String LOCAL = "CHIBA";

    String  searching(String theme);

    default String infoLocal(){
        return this.LOCAL;
    }
}
