package org.hinsteny.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisoka.applicationEvent.EmailService;
import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;

/**
 * @author Hinsteny
 *
 */
@Controller
public class IndexAction {

    private Logger Logger = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	EmailService emailService;
	
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
	
	@Get("/send/email")
    @ResponseBody
    public WebResponse sendEmail(HttpServletRequest request, @RequestParam String to, @RequestParam String title, @RequestParam String content) {
	    emailService.sendEmail(to, title, content);
	    Logger.debug("Send email to {0} and title of {1}", to, title);
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return WebResponse.success("发送成功!");
    }
}
