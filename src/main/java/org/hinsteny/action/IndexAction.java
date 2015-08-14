package org.hinsteny.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisoka.rest.Get;

/**
 * @author Hinsteny
 *
 */
@Controller
public class IndexAction {

	Log log =LogFactory.getLog(this.getClass());
	
	@Get("/")
	@ResponseBody
	public ModelAndView index() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "Hinsteny");
		return new ModelAndView("home").addObject("name", "Hinsteny Hisoka");
	}
	
	@Get("/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("name", "Hinsteny");
		return "home";
	}
}
