package com.hisoka.other.lombok;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by Hinsteny on 2016/1/6.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Student {

    String name;
    int sex;
    Integer age;
    String address;

    List<String> books;

}
