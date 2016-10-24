package com.test.xstream.converter;

import com.hisoka.utils.DateTimeUtil;
import com.hisoka.utils.StringUtil;
import com.thoughtworks.xstream.converters.SingleValueConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/10/24
 * @copyright: 2016 All rights reserved.
 */
public class LocalDateConverter implements SingleValueConverter {

    public static final String SHORT_DATE_FORMAT_STR = DateTimeUtil.SHORT_DATE_FORMAT_STR;
    public static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT_STR);

    public static final String LONG_DATE_FORMAT_STR = DateTimeUtil.LONG_DATE_FORMAT_STR;
    public static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(LONG_DATE_FORMAT_STR);

    public String toString(Object obj) {
        if (obj instanceof LocalDate)
            return ((LocalDate)obj).format(formatterDate);
        else if (obj instanceof LocalDateTime)
            return ((LocalDateTime)obj).format(formatterTime);
        else
            return "";
    }

    public Object fromString(String name) {
        if (StringUtil.isBlank(name)) return null;
        if (SHORT_DATE_FORMAT_STR.length() == name.length())
            return LocalDate.parse(name, formatterDate);
        else if(LONG_DATE_FORMAT_STR.length() == name.length())
            return LocalDateTime.parse(name, formatterTime);
        else
            return null;
    }

    public boolean canConvert(Class type) {
        return type.equals(LocalDate.class) || type.equals(LocalDateTime.class);
    }

}