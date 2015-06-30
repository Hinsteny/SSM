package com.hisoka.rest;

import java.lang.annotation.*;
/**
 * The annotation for Post request
 * this equivalent to @RequestMapping with Put request method
 * @author Hinsteny
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Put {

	String[] value() default{};

}
