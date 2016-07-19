package org.hinsteny.action;

import com.hisoka.rest.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hinsteny
 *
 */
@RequestMapping("/test")
@Controller
public class BerbonAction {

    private Logger logger = LoggerFactory.getLogger(BerbonAction.class);
	
	@RequestMapping("/unpay/font/openCard")
	@ResponseBody
	public void fontOpenCard(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/unpay/back/openCard")
	@ResponseBody
	public void backOpenCard(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

}
