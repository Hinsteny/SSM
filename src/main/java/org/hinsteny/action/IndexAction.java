package org.hinsteny.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Hinsteny
 *
 */
@Controller
public class IndexAction {

	Log log =LogFactory.getLog(this.getClass());
	
	@RequestMapping("/home")
	@ResponseBody
	public ModelAndView index() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "Hinsteny");
		return new ModelAndView("sys-pages/home").addObject("user",result);
	}
}
