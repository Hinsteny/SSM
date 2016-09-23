package org.hinsteny.action;

import com.groovy.Person;
import com.hisoka.rest.Get;
import com.hisoka.result.WebResponse;
import com.lombok.Student;
import org.hinsteny.bean.Book;
import org.hinsteny.bean.Good;
import org.hinsteny.bean.User;
import org.hinsteny.event.service.EmailService;
import org.hinsteny.service.MQService;
import org.hinsteny.service.UserMongoService;
import org.hinsteny.service.UserRedisService;
import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hinsteny
 *
 */
@Controller
public class IndexAction {

    private Logger logger = LoggerFactory.getLogger(IndexAction.class);

	@Value("#{new Boolean('${invalidateHttpSession}')}")
	private boolean invalidateHttpSession = true;

	@Autowired
	EmailService emailService;

	@Resource
	private UserService userService;

	@Resource
	private UserRedisService userRedisService;

	@Resource
	private UserMongoService userMongoService;

	@Autowired
	private MQService mqService;

	@Get("/")
	@ResponseBody
	public ModelAndView index() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "Hinsteny");
		return new ModelAndView("home").addObject("name", "Hinsteny Hisoka");
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, @RequestParam(required=false) String username) {

		return "login";
	}

	@Get("/loginInfo")
	@ResponseBody
	public WebResponse loginInfo(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return WebResponse.build().setResult(username);
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("name", "Hinsteny");
		return "home";
	}

	@Get("/send/email")
    @ResponseBody
    public WebResponse sendEmail(HttpServletRequest request, @RequestParam String to, @RequestParam String title, @RequestParam String content) {
	    emailService.sendEmail(to, title, content);
		if (logger.isDebugEnabled()) logger.debug("Send email to {} and title of {}", to, title);
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
		if (logger.isDebugEnabled()) logger.debug(person.getName());
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
		if (logger.isDebugEnabled()) logger.debug(student.getName());
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

	@Get("/testTransaction/{username}")
	@ResponseBody
	public WebResponse testTransaction(HttpServletRequest request, @RequestParam(required=false) String flag, @PathVariable(value = "username") String username, HttpServletResponse response) {
		if (logger.isInfoEnabled()) logger.info("User ask flag is {}", flag);
		User user = new User();
		user.setUsername(username);
		userService.doNotice(user);
		return WebResponse.success(user);
	}

	@Get("/testRedis/append")
	@ResponseBody
	public WebResponse testRedisAppend(HttpServletRequest request, @RequestParam(name = "username") String username, HttpServletResponse response) {
		User user = new User();
		user.setId(Math.round(Math.round(Math.random())));
		user.setUsername(username);
		return WebResponse.success(userRedisService.create(user));
	}

	@Get("/testRedis/get")
	@ResponseBody
	public WebResponse testRedisGet(HttpServletRequest request, @RequestParam(name = "username") String username, HttpServletResponse response) {
		User user = new User();
		user.setId(Math.round(Math.round(Math.random())));
		user.setUsername(username);
		return WebResponse.success(userRedisService.find(user));
	}

	@Get("/testMongo/create")
	@ResponseBody
	public WebResponse testMongoinsert(HttpServletRequest request, @RequestParam(name = "goodname") String goodname, HttpServletResponse response) {
		Good good = new Good();
		good.setGoodName(goodname);
		good.setDescription("welcome");
		good.setCreateTime(LocalDateTime.now());
		return WebResponse.success(userMongoService.create(good));
	}

	@Get("/testMongo/get")
	@ResponseBody
	public WebResponse testMongoQuery(HttpServletRequest request, @RequestParam(name = "goodname") String goodname, HttpServletResponse response) {
		Good good = new Good();
		good.setGoodName(goodname);
		return WebResponse.success(userMongoService.find(good));
	}

	@Get("/testMQ/send")
	@ResponseBody
	public WebResponse testSendMQ(HttpServletRequest request, @RequestParam(name = "name") String name, HttpServletResponse response) {
		boolean result = mqService.sendMessage(name);
		return WebResponse.success(result);
	}

	@Get("/testMQ/get")
	@ResponseBody
	public WebResponse testGetMQ(HttpServletRequest request, HttpServletResponse response) {
		List<String> result = mqService.getMessage();
		return WebResponse.success(result);
	}

}
