package com.hisoka.utils;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DateTimeUtil
 *
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
public class DateTimeUtil {


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
