package com.hisoka.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.GregorianCalendar;

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
}
