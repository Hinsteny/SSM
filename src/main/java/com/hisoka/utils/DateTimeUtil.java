package com.hisoka.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * DateTimeUtil
 *
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
public class DateTimeUtil extends DateUtils{

    public static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String MAX_LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String EARLY_TIME = "00:00:00 000";
    public static final String LATE_TIME = "23:59:59 999";
    public static final String EARER_IN_THE_DAY = "yyyy-MM-dd 00:00:00.000";
    public static final String LATE_IN_THE_DAY = "yyyy-MM-dd 23:59:59.999";

    /*========================  并发环境下可重复使用的对象集合  =============================*/

    // cache for simpleDateFormat
    private static LinkedBlockingDeque<SimpleDateFormat> sdfc = new LinkedBlockingDeque<>(30);
    /**
     * 对一个日期增加某个时间段，以@see Date 为单位
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addTime(Date date, int field, int second) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(field, second);
        return cal.getTime();
    }

    /**
     * 对一个日期对象按照指定的格式进行格式化(NullPointerException)
     *
     * @param date
     * @param format
     * @return
     */
    public static String format (Date date, String format) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        try {
            return simpleDateFormat.format(date);
        } finally {
            sdfc.offer(simpleDateFormat);
        }
    }

    /**
     * 获取一个用于进行格式化的工具对象 SimpleDateFormat
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat () {
        SimpleDateFormat dateFormat = sdfc.poll();
        if (null == dateFormat) {
            dateFormat = new SimpleDateFormat();
        }
        return dateFormat;
    }
}
