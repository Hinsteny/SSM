package com.test.web.action;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import com.test.base.BaseTest;


/**
 * UserAction.java
 * 
 * @author: Hinsteny
 * @date: 2015年8月8日
 * @copyright: 2015 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring-web.xml" })
public class UserActionTest extends BaseTest {

	private String testUserName = "vip", testPassword = "123456";

	private final String registeUserContentString = "{\"sex\":\"1\","
			+ "\"mobile\":\"13140101234\"," + "\"userName\":\"TestRegister\","
			+ "\"verifyCode\":\"348383\"," + "\"password\":\"123456\"," + "}";

	/* Object data = JSON.parse(orderContentString); */

	@Before
	public void initRegisterUser() throws Exception {
		this.session = (MockHttpSession) mockMvc
				.perform(post("/imitateRegiste").param("testUserName",testUserName))
				.andReturn().getRequest().getSession();
	}

	@Test
	public void testRegisterAndLogin() throws Exception {
		registeUser();
		loginUser();
	}

	// 注册一个23mofang用户
	private void registeUser() throws Exception {
		MvcResult result = mockMvc.perform(
				post("/wechat/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(registeUserContentString).session(session))
				.andReturn();
		this.session = (MockHttpSession) result.getRequest().getSession();
		Assert.assertThat(result.getResponse().getContentAsString(),
				CoreMatchers.containsString("\"code\":0"));
	}

	// 登陆一个23mofang用户
	private void loginUser() throws Exception {
		MvcResult result = mockMvc.perform(
				post("/wechat/login").param("username", testUserName).param("password", testPassword)
				.session(session)).andReturn();
		this.session = (MockHttpSession) result.getRequest().getSession();
		Assert.assertThat(result.getResponse().getContentAsString(),
				CoreMatchers.containsString("\"code\":0"));
	}

	@After
	public void cleanData() {
		
	}
}
