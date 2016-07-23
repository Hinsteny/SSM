package org.hinsteny.action;

import com.groovy.Person;
import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;
import com.lombok.Student;
import org.hinsteny.bean.Book;
import org.hinsteny.bean.Good;
import org.hinsteny.event.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hinsteny
 *
 */
@Controller
public class IndexAction {

    private Logger logger = LoggerFactory.getLogger(IndexAction.class);
	
	@Autowired
	EmailService emailService;
	
	@Get("/")
	@ResponseBody
	public ModelAndView index() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "Hinsteny");
		return new ModelAndView("home").addObject("name", "Hinsteny Hisoka");
	}

	@Get("/login")
	public String login(HttpServletRequest request, Model model, @RequestParam(required=false) String username) {
		return "login";
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
	    logger.debug("Send email to {} and title of {}", to, title);
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return WebResponse.success("发送成功!");
    }

	@Get("/testGroovy")
	@ResponseBody
	public WebResponse testGroovy(HttpServletRequest request, @RequestParam String name, @RequestParam Integer age, @RequestParam(required=false) String address) {
		Person person = new Person();
		person.setName(name);
		person.setAge(age);
		person.setAddress(address);
		logger.debug(person.getName());
		return WebResponse.success(person.toString());
	}

	@Get("/testLombok")
	@ResponseBody
	public WebResponse testLombok(HttpServletRequest request, @RequestParam String name, @RequestParam Integer age, @RequestParam(required=false) String address,
								  @RequestParam(name="book", required=false) String[] books) {
		Student student = new Student();
		student.setName(name);
		student.setAge(age);
		student.setAddress(address);
		student.setBooks(Arrays.asList(books));
		logger.debug(student.getName());
		return WebResponse.success(student.toString());
	}

	@Get("/testJson")
	@ResponseBody
	public WebResponse testJson(HttpServletRequest request, HttpServletResponse response) {
		Good good = new Good();
		good.setGoodName("Apple");
		good.setCategory(001);
		good.setCreateTime(LocalDateTime.now());
		good.setDescription("Test println Json/Object Data!");
		return WebResponse.success(good);
	}

	@Get("/testXml")
	@ResponseBody
	public WebResponse testXml(HttpServletRequest request, HttpServletResponse response) {
		Book good = new Book();
		good.setBookName("Soul");
		good.setCategory(002);
		good.setCreateTime(LocalDateTime.now());
		good.setDescription("Test println Xml/Object Data!");
		return WebResponse.success(good);
	}
}
