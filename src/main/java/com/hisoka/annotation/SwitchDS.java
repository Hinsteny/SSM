package com.hisoka.annotation;

import java.lang.annotation.*;

/**
 * @author Hinsteny
 * @Describtion 切换数据源, 优先切换数据源到[key]所指定的DS, key无效时判断slaver是否为true切换到读的DS上
 * @date 2017/5/12
 * @copyright: 2016 All rights reserved.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchDS {

    /**
     * 数据源的key
     * @return
     */
    String key() default "";

    /**
     * 是否切换到读的DS
     * @return
     */
    boolean slaver() default true;
}
