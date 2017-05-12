package com.test.service;

import com.test.base.SpringContext;
import org.hinsteny.bean.User;
import org.hinsteny.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/4/15
 * @copyright: 2016 All rights reserved.
 */
public class UserServiceTest extends SpringContext{

    @Autowired
    private UserService userService;

    @Test
    public void testQueryUser(){
        User user = new User();
        List<User> users =  userService.listUsers(user);
        Assert.assertNotNull(users);
        System.out.println(users.size());
    }
}
