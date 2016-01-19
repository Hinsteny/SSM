package com.test.others;

import com.hisoka.utils.DESUtil;
import com.sun.glass.ui.SystemClipboard;

/**
 * Test
 *
 * @author Hinsteny
 * @date 2016/1/19
 * @copyright: 2016 All rights reserved.
 */
public class Test {

    @org.junit.Test
    public void doEncrypt() throws Exception{
        System.out.println("Encrypt:  " + DESUtil.encrypt("111-1120-3242"));
    }

    @org.junit.Test
    public void doDecrypt() throws Exception{
        System.out.println("Decrypt: " + DESUtil.decrypt("306f8125cd8d5112b30c6dc968a002b6"));
    }
}
