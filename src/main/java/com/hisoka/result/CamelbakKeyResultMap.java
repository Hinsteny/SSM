package com.hisoka.result;

import java.util.HashMap;

/**
 * 用于MyBatis返回结果时，将key由下划线转为驼峰形式。这里仅仅声明一种Map类型，
 * 配合{@link com.hisoka.result.moon.core.orm.mybatis.wrapper.CamelbakKeyMapWrapperFactory}使用，
 * 在Mybatis的sql映射中配置：
 * <code>
 *     resultType="org.moon.core.orm.mybatis.result.CamelbakKeyResultMap"
 * </code>
 * 或者使用别名：
 * <code>
 *     resultType="CamelbakKeyResultMap"
 * </code>
 * @author Hinsteny
 * @date 2015年7月21日
 * @copyright: 2015 All rights reserved.
 */
@SuppressWarnings("serial")
public class CamelbakKeyResultMap<K,V> extends HashMap<K,V> {

}
