package com.hisoka.utils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * 字符串处理
 * Strings
 * @author Hinsteny
 * @date 2015/10/3
 * @copyright: 2016 All rights reserved.
 */
public final class Strings {

    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     *
     * <pre>
     * Strings.isEmpty(null)      = true
     * Strings.isEmpty("")        = true
     * Strings.isEmpty(" ")       = false
     * Strings.isEmpty("bob")     = false
     * Strings.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if a String is blank. A blank string is one that is {@code null}, empty, or when trimmed using
     * {@link String#trim()} is empty.
     *
     * @param s
     *        the String to check, may be {@code null}
     * @return {@code true} if the String is {@code null}, empty, or trims to empty.
     */
    public static boolean isBlank(final String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * <p>
     * change the first char to lower case of the String.
     * </p>
     *
     * @param s
     * @return
     */
    public static String lowerFirst(String s){
        if(isEmpty(s)){
            return "";
        }
        return s.substring(0, 1).toLowerCase()+s.substring(1);
    }

    /**
     * <p>
     * change the first char to upper case of the String.
     * </p>
     *
     * @param s
     * @return
     */
    public static String upperFirst(String s){
        if(isEmpty(s)){
            return "";
        }
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }

    /**
     * <p>
     * get string content form inputStream with default charset ant return.
     * </p>
     *
     * @param in
     * @return
     */
    public static String getContentFromInputStream(InputStream in){
        return getContentFromInputStream(in,Charset.defaultCharset().name());
    }

    /**
     * <p>
     * get string content form inputStream with given charset
     * </p>
     *
     * @param in
     * @param charset
     * @return
     */
    public static String getContentFromInputStream(InputStream in, String charset){
        StringBuffer dist = new StringBuffer();
        byte[] data = new byte[1024];
        int readNum = -1;
        try{
            while((readNum = in.read(data)) != -1){
                dist.append(new String(data, 0, readNum, charset));
            }
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return dist.toString();
    }

    /**
     * slice up the given string
     * @param s , giver string
     * @param startIndex
     * @param count , max size of substring
     * @return substring
     */
    public static String subString(String s,int startIndex,int count){
    	if(isEmpty(s)){
    		return "";
    	}
    	byte[] stringBytes = s.getBytes();
    	if(startIndex<0){
    		startIndex = 0;
    	}
    	if(count>stringBytes.length-startIndex){
    		count = stringBytes.length-startIndex;
    	}
    	return new String(stringBytes,startIndex,count);
    }
    
    /**
     * change CamBak named method to underline
     * @param src
     * @return
     */
    public static String changeCamelBakToUnderline(String src){
    	return src.replaceAll("([A-Z])", "_$1").toLowerCase();
    }
    
    /**
     * change underline named method to CamBak
     * @param s
     * @return
     */
    public static String changeUnderlineToCamelBak(String s){
        if(isEmpty(s)){
    		return "";
    	}
    	byte[] data = s.getBytes();
    	byte[] result = new byte[data.length];
    	int position = -1;
    	for(int i=0,l=data.length;i<l;i++){
    		if(data[i]==95&&i+1<l){
    			result[++position] = (byte) (data[++i]-32);
    		}else{
    			result[++position] = data[i];
    		}
    	}
    	if(position==-1){
    		return s;
    	}
    	return new String(result,0,position+1);
    }

    /**
     * Returns a quoted string.
     *
     * @param str
     *        a String
     * @return {@code 'str'}
     */
    public static String quote(final String str) {
        return CharUtil.QUOTE + str + CharUtil.QUOTE;
    }

    /**
     * Returns a double quoted string.
     *
     * @param str
     *        a String
     * @return {@code "str"}
     */
    public static String dquote(final String str) {
        return CharUtil.DQUOTE + str + CharUtil.DQUOTE;
    }

    /**
     * 将集合中的所有值连接为以 delimiter分割的字符串，分隔符默认是逗号
     * @param values
     * @param delimiter
     * @return
     */
    public static String join(Collection<? extends Object> values,String delimiter){
    	if(Objects.isNull(values)||values.size()==0){
    		return "";
    	}
        if(isEmpty(delimiter)){
            delimiter = ",";
        }
        boolean begin = true;
    	StringBuffer sb = new StringBuffer();
    	
    	for(Object o:values){
    		if(begin){
    			begin = false;
    		}else{
    			sb.append(delimiter);
    		}
    		sb.append(o);
    	}
    	return sb.toString();
    }
    
    /**
     * 将集合中的所有值,通过StringCustomerHandler处理后,连接为以 delimiter分割的字符串，分隔符默认是逗号
     * @param values
     * @param delimiter
     * @param handler
     * @return
     */
    public static  <T> String  join(Collection<T> values,String delimiter,StringCustomerHandler<T> handler){
    	if(Objects.isNull(values)||values.size()==0){
    		return "";
    	}
        if(isEmpty(delimiter)){
    		delimiter = ",";
    	}
        boolean begin = true;
    	StringBuffer sb = new StringBuffer();
    	for(T o:values){
    		if(begin){
    			begin = false;
    		}else{
    			sb.append(delimiter);
    		}
    		sb.append(handler.handle(o));
    	}
    	return sb.toString();
    }

    /**
     * 将数组中的所有值连接为以 delimiter分割的字符串，分隔符默认是逗号
     * @param values
     * @param delimiter
     * @return
     */
    public static String join(Object[] values,String delimiter){
        if(Objects.isNull(values)||values.length==0){
            return "";
        }

        if(Objects.isNull(delimiter)){
            delimiter = ",";
        }

        boolean begin = true;
        StringBuffer sb = new StringBuffer();

        for(Object o:values){
            if(begin){
                begin = false;
            }else{
                sb.append(delimiter);
            }
            sb.append(o);
        }
        return sb.toString();
    }

    /**
     * 将数组中的所有值,通过StringCustomerHandler处理后,连接为以 delimiter分割的字符串，分隔符默认是逗号
     * @param values
     * @param delimiter
     * @param handler
     * @return
     */
    public static  <T> String  join(T[] values,String delimiter,StringCustomerHandler<T> handler){
        if(Objects.isNull(values)||values.length==0){
            return "";
        }

        boolean begin = true;
        if(Objects.isNull(delimiter)){
            delimiter = ",";
        }
        StringBuffer sb = new StringBuffer();
        for(T o:values){
            if(begin){
                begin = false;
            }else{
                sb.append(delimiter);
            }
            sb.append(handler.handle(o));
        }
        return sb.toString();
    }

    /**
     * 自定义解析值的方法
     */
	public  static interface StringCustomerHandler<T>{
		String handle(T t);
	}

    /**
     * 将字符数组链接为字符串,用于多个字符串链接使用
     * @param strings
     * @return
     */
    public static String connect(String ... strings){
        StringBuilder sb = new StringBuilder();
        for(String s:strings){
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空或者为空字符串
     * @param src
     * @return
     */
    public static boolean isNullOrEmpty(String src){
        return src == null || src.isEmpty() || src.trim().isEmpty();
    }

    /**
     * 将数组对象每个元素加上前缀
     * @param prefix
     * @param elements
     * @return
     */
    public static String[] concatPrefix(String prefix , Object[] elements){
        int length = elements.length;
        String[] results = new String[length];
        for(int p = 0; p < length; p++){
            results[p] = prefix.concat(castToString(elements[p]));
        }
        return results;
    }

    /**
     * 将对象转换为String
     * @param o
     * @return
     */
    public static String castToString(Object o){
        if(Objects.isNull(o)){
            return null;
        }else if(o instanceof String){
            return (String)o;
        }else {
            return o.toString();
        }
    }
}
