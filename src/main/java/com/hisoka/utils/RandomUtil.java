package com.hisoka.utils;

import org.junit.Assert;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/3/16
 * @copyright: 2016 All rights reserved.
 */
public class RandomUtil {

    private static final char[] codes = {'0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z'};

    /**
     * 返回随机字符串，同时[可能]包含数字、大小写字母
     *
     * @param len 字符串长度，不能小于3
     * @return String 随机字符串
     */
    public static String randomStr(int len) {
        if (len < 1) {
            throw new IllegalArgumentException("字符串长度不能小于1");
        }
        // 数组，用于存放随机字符
        char[] chArr = new char[len];

        // charArr[0..len-1]随机生成codes中的字符
        final int right = codes.length;
        for (int i = 0; i < len; i++) {
            chArr[i] = codes[RandomSuptUtil.uniform(0, right)];
        }
//		reOrderChars(chArr);
        return new String(chArr);
    }

    /**
     * 返回随机字符串，只包含数字
     *
     * @param len 字符串长度，不能小于3
     * @return String 随机字符串
     */
    public static String randomNum(int len) {
        if (len < 1) {
            throw new IllegalArgumentException("字符串长度不能小于1");
        }
        // 数组，用于存放随机字符
        char[] chArr = new char[len];

        // charArr[0..len-1]随机生成codes中的字符
        final int right = 10;
        for (int i = 0; i < len; i++) {
            chArr[i] = codes[RandomSuptUtil.uniform(0, right)];
        }
//		reOrderChars(chArr);
        return new String(chArr);
    }

    @SuppressWarnings("unused")
    private static void reOrderChars (char[] chArr){
        Assert.assertNotNull(chArr);
        // 将数组chArr随机排序
        for (int i = 0, j = chArr.length; i < j; i++) {
            int r = i + RandomSuptUtil.uniform(j - i);
            char temp = chArr[i];
            chArr[i] = chArr[r];
            chArr[r] = temp;
        }
    }

    public static void main(String[] args){
        System.out.println(RandomUtil.randomStr(32));
        System.out.println(RandomUtil.randomNum(32));
    }
}
