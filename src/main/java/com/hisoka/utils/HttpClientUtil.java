package com.hisoka.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * HttpClient 请求的常用方法封装
 *
 * jdk 1.7
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
public final class HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * get json content and transform to Map from the response content
	 *
	 * @param response
	 * @return
     */
	public static Map<String,Object> getContent(HttpResponse response){
		Map<String, Object> result = null;
		HttpEntity httpEntity = response.getEntity();
		try (
			InputStream in = httpEntity.getContent();
		){
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(in, HashMap.class);
		}catch (IOException e) {
			logger.error("Get json content from response entity had occurred an error: {}", e.toString());
			return result;
		}
		return result;
	}

	/**
	 * get json content and transform to Map from the urlStr by Get method
	 * If occuer one error just return null.
	 * @param urlStr
	 * @return
	 */
	public static Map<String,Object> readHttpContentGet(String urlStr){
		HttpResponse response;
		try {
			response = Request.Get(urlStr).execute().returnResponse();
		}catch (IOException e) {
			logger.error("Get resouse from {} had occurred an error: {}", urlStr, e.toString());
			return null;
		}
		return getContent(response);
	}


	/**
	 * get json content and transform to Map from the urlStr by Post method
	 * If occuer one error just return null.
	 * @param urlStr
	 * @return
	 */
	public static Map<String,Object> readHttpContentPost(String urlStr, Map data){
		HttpResponse response;
		try {
			response = Request.Post(urlStr).bodyString(MapUtil.renderDataToStr(data), ContentType.APPLICATION_JSON).execute().returnResponse();
		}catch (IOException e) {
			logger.error("Get resouse from {} had occurred an error: {}", urlStr, e.toString());
			return null;
		}
		return getContent(response);
	}


}
