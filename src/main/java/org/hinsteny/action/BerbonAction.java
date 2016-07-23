package org.hinsteny.action;

import com.hisoka.rest.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value ="/unpay/font/openCard", method = {RequestMethod.GET, RequestMethod.PUT})
	@ResponseBody
	public void fontOpenCard(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/unpay/back/openCard")
	@ResponseBody
	public void backOpenCard(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/yepay/Gw/inform")
	@ResponseBody
	public void yeGwPayInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

}
