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
import java.io.IOException;
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
	
	@RequestMapping(value ="/unpay/font/openCard", method = {RequestMethod.GET, RequestMethod.POST})
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

	@RequestMapping("/testYeBaoPay/B2C")
	public void testYeBaoPayB2C(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String html = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\"/></head><body><form name = \"pay_form\" action=\"https://www.yeepay.com/app-merchant-proxy/node\" method=\"post\"><input type=\"hidden\" name=\"pt_Mobile\" id=\"pt_Mobile\" value=\"\"/><input type=\"hidden\" name=\"pa_MP\" id=\"pa_MP\" value=\"测试的订单\"/><input type=\"hidden\" name=\"pr_NeedResponse\" id=\"pr_NeedResponse\" value=\"1\"/><input type=\"hidden\" name=\"p8_Url\" id=\"p8_Url\" value=\"http://wxkf.berbon.com/ybcallback/callback/return/123456\"/><input type=\"hidden\" name=\"pd_FrpId\" id=\"pd_FrpId\" value=\"CCB-NET-B2C\"/><input type=\"hidden\" name=\"pt_PostalCode\" id=\"pt_PostalCode\" value=\"\"/><input type=\"hidden\" name=\"p2_Order\" id=\"p2_Order\" value=\"201607211291\"/><input type=\"hidden\" name=\"p6_Pcat\" id=\"p6_Pcat\" value=\"食物\"/><input type=\"hidden\" name=\"pt_TeleNo\" id=\"pt_TeleNo\" value=\"\"/><input type=\"hidden\" name=\"p3_Amt\" id=\"p3_Amt\" value=\"0.05\"/><input type=\"hidden\" name=\"pm_Period\" id=\"pm_Period\" value=\"\"/><input type=\"hidden\" name=\"p5_Pid\" id=\"p5_Pid\" value=\"哇哈哈\"/><input type=\"hidden\" name=\"pn_Unit\" id=\"pn_Unit\" value=\"\"/><input type=\"hidden\" name=\"hmac\" id=\"hmac\" value=\"14a0bd393692e0fb9f70b6b54fb6f014\"/><input type=\"hidden\" name=\"pt_Address\" id=\"pt_Address\" value=\"\"/><input type=\"hidden\" name=\"pt_UserName\" id=\"pt_UserName\" value=\"\"/><input type=\"hidden\" name=\"p0_Cmd\" id=\"p0_Cmd\" value=\"Buy\"/><input type=\"hidden\" name=\"pt_Email\" id=\"pt_Email\" value=\"\"/><input type=\"hidden\" name=\"p7_Pdesc\" id=\"p7_Pdesc\" value=\"最爱的饮品\"/><input type=\"hidden\" name=\"p1_MerId\" id=\"p1_MerId\" value=\"10012423519\"/><input type=\"hidden\" name=\"p4_Cur\" id=\"p4_Cur\" value=\"CNY\"/><input type=\"hidden\" name=\"p9_SAF\" id=\"p9_SAF\" value=\"0\"/></form></body><script type=\"text/javascript\">document.forms['pay_form'].submit();</script></html>";
//		response.setHeader("referer", "http://www.19fei.com");
		response.setCharacterEncoding("GBK");
		response.getWriter().write(html);
	}

	@RequestMapping(value ="/yspay/font", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void ysFontInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/yspay/back")
	@ResponseBody
	public void ysBackInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/jsupay/Gw/inform")
	@ResponseBody
	public void jsuGwPayInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("/jsupay/Gw/notify")
	@ResponseBody
	public void jsuGwPayNotify(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

}
