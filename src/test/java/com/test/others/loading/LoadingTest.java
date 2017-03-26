package com.test.others.loading;

import org.junit.Test;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/1/20
 * @copyright: 2016 All rights reserved.
 */
public class LoadingTest {

    @Test
    public void TestLoading() throws ClassNotFoundException{
        Class.forName("com.test.others.loading.BeLoad");
    }
}
