package com.test.xstream.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @date 2016/10/14
 * @copyright: 2016 All rights reserved.
 */
public class Birthday {

    private LocalDate date = LocalDate.now();

    private LocalDateTime dateTime = LocalDateTime.now();

    private String time = "";

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Birthday{" +
                "date=" + date +
                ", dateTime=" + dateTime +
                ", time='" + time + '\'' +
                '}';
    }
}
