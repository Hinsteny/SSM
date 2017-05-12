package org.hinsteny.action;

import com.hisoka.rest.Get;
import org.hinsteny.bean.User;
import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/4/17
 * @copyright: 2016 All rights reserved.
 */
@Controller
@RequestMapping("/test")
public class TestAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Resource
    private UserService userService;

    @Get("/createUser")
    public String createUsers(Model model, @RequestParam String username) {
        User param = new User();
        param.setUsername(username);
        Long id = userService.createUser(param);
        logger.error("Create user with name {}, then id is {}", username, id);
        return "UserList";
    }

    @Get("/getUsers")
    public String showUsers(Model model, @RequestParam(required=false) String username) {
        User param = new User();
        param.setUsername(username);
        List<User> users = userService.listUsers(param);
        logger.error("show users {}, {}", username, users);
        model.addAttribute("users", users);
        return "UserList";
    }

    @Get("/updateUsers")
    public String updateUsers(Model model, @RequestParam String username) {
        User param = new User();
        param.setUsername(username);
        Long count = userService.updateUser(param);
        logger.error("update users {}, infect {}", username, count);
        return "UserList";
    }

    @Get("/deleteUsers")
    public String deleteUsers(Model model, @RequestParam String username) {
        User param = new User();
        param.setUsername(username);
        Long count = userService.deleteUser(param);
        logger.error("delete users {}, infect {}", username, count);
        return "UserList";
    }

}
