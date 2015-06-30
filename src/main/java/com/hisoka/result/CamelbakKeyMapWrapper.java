package com.hisoka.result;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 用于转换字段名为驼峰形式的包装器
 * @author Hinsteny
 *
 */
public class CamelbakKeyMapWrapper extends MapWrapper {
    private Map<String, Object> map ;
    public CamelbakKeyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject,map);
        this.map = map;
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            setCollectionValue(prop, collection, value);
        } else {
            map.put(prop.getName(), value);
        }
    }
}
