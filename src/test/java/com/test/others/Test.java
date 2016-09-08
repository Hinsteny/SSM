package com.test.others;

import com.hisoka.utils.DESUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @org.junit.Test
    public void regEx() throws Exception{
        String str="|        |  |";
        String regEx="[1-9]+";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        boolean rs=m.find();
        System.out.println(rs);
    }
}
