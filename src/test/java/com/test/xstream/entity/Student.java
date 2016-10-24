package com.test.xstream.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Hinsteny
 * @date 2016/10/14
 * @copyright: 2016 All rights reserved.
 */
@Data
public class Student {

    private int id;
    private String name;
    private String email;
    private String address;
    private Birthday birthday;

    public String toString() {
        return this.name + "#" + this.id + "#" + this.address + "#" + this.birthday + "#" + this.email;
    }


    @Data
    public static class StuList {

        private String name;

        private List<Student> students;

    }
}
