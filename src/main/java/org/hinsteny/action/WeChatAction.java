package org.hinsteny.action;

import com.hisoka.Component.WeChatManager;
import com.hisoka.rest.Get;
import com.hisoka.support.weChat.WeChatPageInterface;
import com.hisoka.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * WeChatAction
 *
 * @author Hinsteny
 * @date 2016/1/14
 * @copyright: 2016 All rights reserved.
 */
@Controller
@RequestMapping("/weChat")
public class WeChatAction {

    private Logger logger = LoggerFactory.getLogger(WeChatAction.class);
    private final String BASE_HOME = "weChat/";

    @Autowired
    WeChatManager weChatManager;

    @Get(value = {"/", "/index"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        String openId = (String) session.getAttribute(WeChatManager.WECHATOPENID);
        String access_token = weChatManager.getWechatAPPAccessToken();
        logger.info("Visit url: {}, AccessToken: {}, OpenID: {}", WebUtil.getRequsetUrl(request, true), access_token, openId);
        Map userInfo = WeChatManager.getWechatUserInfo(access_token, openId);
        logger.info(userInfo == null ? "Can't get the wechat user info!" : "Get wechat username: {}, sex: {}", userInfo.get("nickname"), userInfo.get("sex"));
        model.addAttribute("userInfo", userInfo);
        return BASE_HOME + "index";
    }

    /**
     * 微信请求，让微信用户进行页面授权拿到微信用户信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Get("/page")
    public String page (HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        HttpSession session = request.getSession();
        String jsapiTicjet = weChatManager.getWeChatJsapiTicket();
        logger.info("Visit url: {}, and OpenID: {}, jsapiTicjet: {}", WebUtil.getRequsetUrl(request, true), session.getAttribute(WeChatManager.WECHATOPENID), jsapiTicjet);
        Map<String, String> signData = WeChatManager.getWechatShareSign(request, jsapiTicjet);
        session.setAttribute("sign", signData);
        return BASE_HOME + "page";
    }

    @Get("/activity")
    public String activity(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
        HttpSession session = request.getSession();
        logger.info("Visit url: {}, and OpenID: {}", WebUtil.getRequsetUrl(request, true), session.getAttribute(WeChatManager.WECHATOPENID));

        //尝试，为了区分微信的两种授权，Hinsteny在这里放一个标志参数呀
        Integer isUserinfoScope = (Integer)session.getAttribute("isUserinfoScope");
        if(isUserinfoScope == null){
            isUserinfoScope = 0;
        }else if(isUserinfoScope == 0){
            isUserinfoScope = 1;
        }else if(isUserinfoScope == 1){
            isUserinfoScope = 2;
        }
        session.setAttribute("isUserinfoScope", isUserinfoScope);

        Map wechatUser = (Map) session.getAttribute("wechatUser");

        //检查是否有微信用户授权信息，无则go to请求
        String oauthState = request.getParameter("state");
        if(isUserinfoScope < 1 || (wechatUser == null && oauthState == null)){
            logger.debug("This is request from user, so we do the wechatUserAuth for.");
            String redirectUri =  WebUtil.getRequsetUrl(request, true);
            try {
                return "redirect:" + WeChatManager.getWechatUserAuthorizeSenseUrl(redirectUri);
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug(e.toString());
            } catch (ServletException e) {
                e.printStackTrace();
                logger.debug(e.toString());
            }
        }
        //已经经历过微信授权的请求
        String oauthCode = request.getParameter("code");
        if(wechatUser == null && oauthCode == null){
            logger.debug("天赋儿童点赞:用户拒绝授权点赞页!");
            throw new Exception("天赋儿童点赞:用户拒绝授权点赞页!");
        }

        if(wechatUser == null){
            try {
                Map<String, Object> oauthData = WeChatPageInterface.getWechatPageAccessToken(oauthCode);
                if (oauthData == null)
                    throw new Exception("Can't get the oauth accessToken with oauthCode: " + oauthCode);
                wechatUser = WeChatPageInterface.getWechatUserDetailInfo((String) oauthData.get(WeChatManager.ACCESS_TOKEN), (String) oauthData.get(WeChatManager.OPENID));
            } catch (Exception e) {
                logger.debug("Fetch user info failed for userName: {}");
                throw new Exception("天赋儿童点赞:获取微信用户详细信息出错:"+ e.toString());
            }
        }
        session.setAttribute("wechatUser", wechatUser);
        model.addAttribute("userInfo", wechatUser);
        logger.info("Get weChat userInfo, userName: {}, userImg: {}", wechatUser.get("nickname"), wechatUser.get("headimgurl"));
        return BASE_HOME + "activity";
    }

}
