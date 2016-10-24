package com.test.xstream.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @date 2016/10/14
 * @copyright: 2016 All rights reserved.
 */
@Data
public class Birthday {

    private LocalDate date = LocalDate.now();

    private LocalDateTime dateTime = LocalDateTime.now();

    private String time = "";

    @Override
    public String toString() {
        return "Birthday{" +
                "date=" + date +
                ", dateTime=" + dateTime +
                ", time='" + time + '\'' +
                '}';
    }
}
