package com.hisoka.rest;

import java.lang.annotation.*;
/**
 * The annotation for Post request
 * this equivalent to @RequestMapping with Get request method
 * @author Hinsteny
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {

	String[] value() default{};

}
