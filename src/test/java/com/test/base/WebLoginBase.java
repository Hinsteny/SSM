package com.test.base;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * WebLoginBase
 *
 * @author: Hinsteny
 * @date: 2015-8-7
 * @copyright: 2015 All rights reserved.
 */
public class WebLoginBase extends SpringContext {

    protected MockMvc mockMvc;

    protected MockHttpSession session;//会话

    private String testUserName = "vip", testPassword = "123456";

    @Before
    public void init() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter((Filter) wac.getBean("authorityFilter"), "/*").build();
    }

    protected void loginUser(String userName, String passWord) throws Exception {
        String username = (userName != null && userName.length() > 0) ? userName : testUserName;
        String password = (passWord != null && passWord.length() > 0) ? passWord : testPassword;
        //登陆测试用户得到session
        this.session = (MockHttpSession) mockMvc.perform(post("/login").param("username", username)
                .param("password", password)).andReturn().getRequest().getSession();
    }
}
