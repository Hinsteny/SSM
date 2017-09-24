package com.test.others;

import com.hisoka.utils.DESUtil;
import com.hisoka.utils.FileUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OtherTest
 *
 * @author Hinsteny
 * @date 2016/1/19
 * @copyright: 2016 All rights reserved.
 */
public class OtherTest {

    @org.junit.Test
    public void NullTest() throws Exception{
        String str = null;
        if (null != (str = getStr()))
            System.err.println("y" + str);
        System.err.println("x");
    }

    private String getStr () {
        return null;
    }

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

    @org.junit.Test
    public void testIndexOf() throws Exception{
        String fileName = getFilePath("20160925", "123", "b2C.cvs");
        String[] fileNames = new String[2];
        int indexOf = fileName.lastIndexOf(File.separator);
        if(indexOf > -1){
            fileNames[0] = fileName.substring(0, indexOf);
            fileNames[1] = fileName.substring(indexOf);
        }else {
            fileNames[0] = "";
            fileNames[1] = fileName;
        }
    }

    @org.junit.Test
    public void testFormatDate() throws Exception{
        String dateString = "2016-09-27";
        String format = "yyyy-MM-dd 00:00:00.000";
        Date date = null;
        try{
            date = parserStringToDate(dateString, format);
        }catch(Exception e){
            throw e;
        }
        if (date != null)
            System.out.println(date.toString());
    }

    @org.junit.Test
    public void testCreateFile() throws Exception{
        File file = new File("D://logs/hisoka.txt");

    }

    @org.junit.Test
    public void testAddDate() throws Exception{
        Date date = new Date();
        Date new_date = addDateDay(date, -1);
        Date new_date_ = addDateDay(new_date, 2);
        System.out.println(date.toString());
        System.out.println(new_date.toString());
        System.out.println(new_date_.toString());
    }

    @org.junit.Test
    public void testFileSize(){
        File file = new File("D://logs/");
        long size = FileUtil.getFileSize(file);
        System.out.println(size);
        file = new File("D://test.txt");
        size = FileUtil.getFileSize(file);
        System.out.println(size);
    }

    @org.junit.Test
    public void testListAddNull(){
        List<String> lists = new ArrayList<>(12);
        System.out.println(lists.size());
        lists.add("Hinsteny");
        System.out.println(lists.size());
        lists.add(null);
        lists.add(null);
        System.out.println(lists.size());
    }

    private String getFilePath(String... args){
        StringBuilder sb = new StringBuilder();
        for (String str:args) {
            sb.append(str).append(File.separator);
        }
        return sb.substring(0, sb.length()-1);
    }

    private static Date parserStringToDate(String dateString, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    protected Date addDateDay(Date date, int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

}
