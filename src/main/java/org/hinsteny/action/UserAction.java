package org.hinsteny.action;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;

@Controller
@RequestMapping("/user")
public class UserAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);
    
	@Resource
	private UserService userService;

	public String index() {
		return null;
	}

	@Get("/regist")
	@ResponseBody
	public WebResponse regist(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("username", username);
		param.put("password", password);
		userService.create(param);
		return WebResponse.build().setResult(true);
	}
	
	@Get("/getUsers")
    public String showUsers(HttpServletRequest request, Model model, @RequestParam(required=false) String username) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("username", username);
        List<HashMap<String,Object>> users = userService.listUsers(param);
        model.addAttribute("users", users);
        return "UserList";
    }
}
