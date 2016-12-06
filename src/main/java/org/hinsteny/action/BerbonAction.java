package org.hinsteny.action;

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

	@RequestMapping("/yepay/Gw/pay")
	@ResponseBody
	public void yeGwPay(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\"/></head><body><form name = \"pay_form\" action=\"https://www.yeepay.com/app-merchant-proxy/node\" method=\"post\"><input type=\"hidden\" name=\"pa_MP\" id=\"pa_MP\" value=\"Goods Mp\"/><input type=\"hidden\" name=\"p8_Url\" id=\"p8_Url\" value=\"http://cashier.berbon.com/callback/return/012016110909582800001201258834\"/><input type=\"hidden\" name=\"pd_FrpId\" id=\"pd_FrpId\" value=\"ICBC-NET-B2C\"/><input type=\"hidden\" name=\"p2_Order\" id=\"p2_Order\" value=\"012016110909582800001201258834\"/><input type=\"hidden\" name=\"p6_Pcat\" id=\"p6_Pcat\" value=\"Goods kind\"/><input type=\"hidden\" name=\"p3_Amt\" id=\"p3_Amt\" value=\"0.1\"/><input type=\"hidden\" name=\"pm_Period\" id=\"pm_Period\" value=\"60\"/><input type=\"hidden\" name=\"p5_Pid\" id=\"p5_Pid\" value=\"GoodsName\"/><input type=\"hidden\" name=\"pn_Unit\" id=\"pn_Unit\" value=\"minute\"/><input type=\"hidden\" name=\"hmac\" id=\"hmac\" value=\"1678755c10cd06e060e8d8db41be78f0\"/><input type=\"hidden\" name=\"p0_Cmd\" id=\"p0_Cmd\" value=\"Buy\"/><input type=\"hidden\" name=\"p7_Pdesc\" id=\"p7_Pdesc\" value=\"Good describtion\"/><input type=\"hidden\" name=\"p1_MerId\" id=\"p1_MerId\" value=\"10012423519\"/><input type=\"hidden\" name=\"p4_Cur\" id=\"p4_Cur\" value=\"CNY\"/><input type=\"hidden\" name=\"p9_SAF\" id=\"p9_SAF\" value=\"0\"/></form></body><script type=\"text/javascript\">document.forms['pay_form'].submit();</script></html>";
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().println(requestr);
		} catch (Exception e){
			System.out.println(e.toString());
		}

	}

	@RequestMapping("/yepay/Gw/inform")
	@ResponseBody
	public void yeGwPayInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("yspay/back")
	@ResponseBody
	public void ysGwPayBackInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

	@RequestMapping("yspay/font")
	@ResponseBody
	public void ysGwPayFontInform(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String[]> result = new HashMap<String, String[]>();
		request.getParameterMap();
		result.putAll(request.getParameterMap());
		System.out.println(result);
	}

}
