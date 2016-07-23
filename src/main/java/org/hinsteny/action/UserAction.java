package org.hinsteny.action;

import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;
import org.hinsteny.bean.User;
import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);
    
	@Resource
	private UserService userService;

	public String index() {
		return null;
	}

	@Get("/login")
	@ResponseBody
	public WebResponse userLogin(HttpServletRequest request, Model model, @RequestParam String username) {
		User user = new User();
		user.setUsername(username);
		user = userService.find(user);
		return WebResponse.build().setResult(user);
	}

	@Get("/regist")
	@ResponseBody
	public WebResponse regist(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		User param = new User();
		param.setUsername(username);
		param.setPassword(password);
		userService.create(param);
		return WebResponse.build().setResult(true);
	}
	
	@Get("/getUsers")
    public String showUsers(HttpServletRequest request, Model model, @RequestParam(required=false) String username) {
		User param = new User();
		param.setUsername(username);
        List<User> users = userService.listUsers(param);
        model.addAttribute("users", users);
        return "UserList";
    }

}
