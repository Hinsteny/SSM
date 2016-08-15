package org.hinsteny.action;

import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;
import com.hisoka.security.AppAuthenticationManager;
import org.hinsteny.bean.User;
import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);

	@Value("#{new Boolean('${invalidateHttpSession}')}")
	private boolean invalidateHttpSession = true;

	@Resource
	private UserService userService;

	@Get("/regist")
	@ResponseBody
	public WebResponse regist(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		User param = new User();
		param.setUsername(username);
		param.setPassword(password);
		userService.create(param);
		return WebResponse.build().setResult(true);
	}

	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public WebResponse userLogin(HttpServletRequest request, Model model, @RequestParam String username) {
		User user = new User();
		user.setUsername(username);
		user = userService.find(user);
		if (user != null && userService.login(user))
			return WebResponse.build().setResult("Login success!");
		else
			return WebResponse.build().setResult("Login failed!");
	}

	@RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public WebResponse userLogout(HttpServletRequest request, @RequestParam(required=false) String username) {
		//先处理下session
		if (invalidateHttpSession) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}
		User user = new User();
		user.setUsername(username);
		user = userService.find(user);
		if (user != null && userService.logout(user)){
			return WebResponse.build().setResult("logout success!");
		} else{
			return WebResponse.build().setResult("logout failed!");
		}
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
