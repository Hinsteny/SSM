package com.hisoka.rest;

import java.lang.annotation.*;
/**
 * The annotation for Post request
 * this equivalent to @RequestMapping with Delete request method
 *
 * @author Hinsteny
 * @date 2015年7月21日
 * @copyright: 2015 All rights reserved.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {

	String[] value() default{};

}
