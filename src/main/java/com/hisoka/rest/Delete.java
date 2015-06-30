package com.hisoka.rest;

import java.lang.annotation.*;
/**
 * The annotation for Post request
 * this equivalent to @RequestMapping with Delete request method
 * @author Hinsteny
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delete {

	String[] value() default{};

}
