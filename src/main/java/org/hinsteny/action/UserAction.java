package org.hinsteny.action;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hinsteny.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hisoka.result.WebResponse;

@Controller
@RequestMapping("/user")
public class UserAction {

	@Resource
	private UserService userService;

	public String index() {
		return null;
	}

	@RequestMapping("/regist")
	@ResponseBody
	public WebResponse regist(HttpServletRequest request, String name, String password) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("password", password);
		userService.create(param);
		return WebResponse.build().setResult(true);
	}
}
