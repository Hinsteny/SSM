package com.hisoka.utils;

import java.security.Key;

import javax.crypto.Cipher;


/**
 * DESUtil.java
 * 
 * @author: Hinsteny
 * @date: 2015年11月24日
 * @copyright: 2015 All rights reserved.
 * 
 */
public final class DESUtil {

    private static final String keySecret = "snowSecret";
    
    private static Cipher encryptCipher;//加密工具
    private static Cipher decryptCipher;//解密工具
    
    static{
        try {
            Key key = getKey(keySecret.getBytes());    
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 加密字符串
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String encrypt(String strIn) throws Exception {    
        return byteArr2HexStr(encrypt(strIn.getBytes()));    
    }
    
    /**
     * 加密字节数组
     * @param arrB
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] arrB) throws Exception {    
        return encryptCipher.doFinal(arrB);    
    }
    
    /**
     * 解密字符串
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String decrypt(String strIn) throws Exception {    
        return new String(decrypt(hexStr2ByteArr(strIn)));    
    } 
    
    /**
     * 解密字节数组
     * @param arrB
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] arrB) throws Exception {    
        return decryptCipher.doFinal(arrB);    
    }  

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813
     * @param arrB
     * @return
     * @throws Exception
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0    
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }    
        return sb.toString();
    }
    
    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)  
     * 互为可逆的转换过程  
     * @param strIn
     * @return
     * @throws Exception
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {    
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2    
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位  
     * @param arrBTmp
     * @return
     * @throws Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）    
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位    
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) { 
            arrB[i] = arrBTmp[i];
        }    
        // 生成密钥    
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");    
        return key;    
    }    
}
