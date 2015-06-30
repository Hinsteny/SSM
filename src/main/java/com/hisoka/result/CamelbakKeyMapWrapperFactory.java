package com.hisoka.result;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * 用于转换字段名为驼峰形式的适配工厂
 * <p>使用方式：</p>
 * <p>
 *     配置在mybatis的配置文件中：
 *     <code>
 *      &lt;objectWrapperFactory type="org.moon.core.orm.mybatis.wrapper.CamelbakKeyMapWrapperFactory"&gt;&lt;/objectWrapperFactory&gt;
 *     </code>
 * </p>
 * @author Hinsteny
 */
public class CamelbakKeyMapWrapperFactory implements ObjectWrapperFactory {
	
    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof CamelbakKeyResultMap;
    }

    @SuppressWarnings("unchecked")
	@Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new CamelbakKeyMapWrapper(metaObject,(Map<String, Object>)object);
    }
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException {
    	String s = "xcontact,contact,contactTitle";
    	Pattern p = Pattern.compile("[contact,|,contact|,contact,]");
    	Matcher m = p.matcher(s);
    	m.find();
    	System.out.println(m.group());
    	System.out.println(s.replaceAll("(?!\\w)(contact)", "xxx"));
	}
}
