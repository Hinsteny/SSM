package com.hisoka.support.weChat;

import com.hisoka.Component.WeChatManager;
import com.hisoka.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * WeChatPageInterface
 *
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
public class WeChatPageInterface {

    private static final Logger logger = LoggerFactory.getLogger(WeChatPageInterface.class);

    /**
     * 根据微信用户网页授权得到的授权码获取用户信息
     *
     * @param oauthCode
     * @return
     *
     * @throws Exception
     */
    public static Map<String, Object> getWechatUserInfo(String oauthCode) throws Exception {
        Map<String, Object> accessToken = getWechatPageAccessToken(oauthCode);
        if (accessToken == null) {
            logger.debug("Can't get the accessToken!");
            throw new Exception("Can't get the accessToken!");
        }
        Map<String, Object> userInfo = getWechatUserDetailInfo((String) accessToken.get(WeChatManager.ACCESS_TOKEN),(String) accessToken.get(WeChatManager.OPENID));
        if (userInfo == null) {
            logger.debug("Can't get the userInfo!");
            throw new Exception("Can't get the userInfo!");
        }
        return userInfo;
    }

    /**
     * <p>
     * 微信获取授权成功:{ "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID", "scope":"SCOPE", "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" }
     * </p>
     * <p>
     * 微信获取授权失败:{"errcode":40029,"errmsg":"invalid code"}
     * </p>
     *
     * 请求微信公众号的网页授权，得到用户OpenID和access_token
     * 微信网页授权是通过OAuth2.0机制实现的，在用户授权给公众号后，公众号可以获取到一个网页授权特有的接口调用凭证（ 网页授权access_token），
     * 通过网页授权access_token可以进行授权后接口调用，如获取用户基本信息；
     *
     * @return 如果没有获取到有效的凭据，就返回 null
     */
    public static Map<String, Object> getWechatPageAccessToken(String oauthCode) {
        StringBuilder accessTokenUrl = new StringBuilder().append(WeChatManager.weChatAuthAccessTokenPage).append(WeChatManager.getWECHATAPPID())
                        .append("&secret=").append(WeChatManager.getWECHATAPPSECRET()).append("&code=")
                        .append(oauthCode).append("&grant_type=").append("authorization_code");
        Map<String, Object> accessToken = HttpClientUtil.readHttpContentGet(accessTokenUrl.toString());
        if (accessToken != null && accessToken.get(WeChatManager.ACCESS_TOKEN) != null) {
            return accessToken;
        }
        return null;
    }

    /**
     * <p>
     * 微信用户详情获取成功:{
     *      "openid":" OPENID",
     *      " nickname": NICKNAME,
     *      "sex":"1",
     *      "province":"PROVINCE"
     *      "city":"CITY",
     *      "country":"COUNTRY",
     *      "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
     *      "privilege":[
     *          "PRIVILEGE1"
     *          "PRIVILEGE2"
     *      ],
     *      "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     *  }
     * </p>
     * <p>
     * 微信用户详情获取失败:{"errcode":40003,"errmsg":" invalid openid "}
     * </p>
     * 请求微信用户的基本信息 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息
     *
     * @return 如果没有获取到有效的用户信息，就返回 null
     */
    public static Map<String, Object> getWechatUserDetailInfo(String pageAccessToken, String userOpenid) {
        StringBuilder userInfoUrl = new StringBuilder().append(WeChatManager.WECHATUSERINFOPAGE).append(pageAccessToken)
                        .append("&openid=").append(userOpenid).append("&lang=").append("zh_CN");
        Map<String, Object> userInfo = HttpClientUtil.readHttpContentGet(userInfoUrl.toString());
        if (userInfo != null && userInfo.get(WeChatManager.OPENID) != null) {
            logger.debug("Get the wechat user detail information succeed with user nickName: {}!", userInfo.get("nickname"));
            return userInfo;
        }
        return null;
    }

//    /**
//     * 获取微信请求的snsapi_userinfo授权信息
//     * @param request
//     * @return "redirect:url",请求授权;<br> "disagree", 用户拒绝授权; <br>"fail", 授权失败;<br> "success", 授权成功
//     */
//    public static String getWechatUserByUserinfoScope(HttpServletRequest request) {
//        // 判断是否已有snsapi_userinfo的授权信息
//        WechatUser wechatUser = (WechatUser) WebUtils.getSessionAttribute("wechatUser");
//        // 记录是否已经请求过snsapi_userinfo的授权
//        Boolean isUserinfoScope = (Boolean) WebUtils.getSessionAttribute("isUserinfoScope");
//        if (wechatUser == null) {
//            if (isUserinfoScope == null) {
//                try {
//                    logger.debug("开始获取当前用户{}的snsapi_userinfo授权", WebUtils.getCurrentUser().getUsername());
//                    WebUtils.setSessionAttribute("isUserinfoScope", true);
//                    return "redirect:"
//                            + WeChatUtil.askWechatUserAuthorize(request.getRequestURL().toString());
//                } catch (IOException e) {
//                    logger.debug(e.toString());
//                } catch (ServletException e) {
//                    logger.debug(e.toString());
//                }
//            }
//
//            String oauthCode = request.getParameter("code");
//            if (oauthCode == null) {
//                logger.debug("用户{}拒绝对snsapi_userinfo授权", WebUtils.getCurrentUser().getId());
//                return "disagree";
//            }
//
//            try {
//                JSONObject wechatUserInfo = WeChatUtil.getWechatUserInfo(oauthCode);
//                wechatUser = wechatInfoToWechatUser(wechatUserInfo);
//                WebUtils.setSessionAttribute("wechatUser", wechatUser);
//            } catch (Exception e) {
//                logger.debug("Fetch user info failed for userName: {}", WebUtils.getCurrentUser().getUsername());
//                return "fail";
//            }
//        }
//        return "success";
//    }


    /*=================== private methond ======================*/
    /**
     *
     * <p>
     * 检查成功:{ "errcode":0,"errmsg":"ok"}
     * </p>
     * <p>
     * 检查失败:{ "errcode":40003,"errmsg":"invalid openid"}
     * </p>
     * 检查当前的page_access_token是否还有效
     *
     * @param accessToken
     * @param openid
     * @return
     */
    private static boolean checkUserPageTokenValid(String accessToken, String openid) {
        StringBuilder checkTokenUrl = new StringBuilder().append(WeChatManager.weChatAuthCheckTokenPage).append(accessToken)
                .append("&openid=").append(openid);
        Map checkResult = HttpClientUtil.readHttpContentGet(checkTokenUrl.toString());
        if (checkResult != null && WeChatManager.OK.equalsIgnoreCase(checkResult.get(WeChatManager.ERRMSG).toString())){
            logger.info("检查access_token有效");
            return true;
        }
        logger.info("检查access_token无效");
        return false;
    }

    /**
     * <p>
     * 刷新成功:{ "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID", "scope":"SCOPE" }
     * </p>
     * <p>
     * 刷新失败:{"errcode":40029,"errmsg":"invalid code"}
     * </p>
     *
     * 刷新当前的page access_token, 如果没有获取到有效的凭据，就返回 null
     *
     * @param refreshToken
     * @return
     */
    private Map<String, Object> refreshUserPageTokenValid(String refreshToken) {
        StringBuilder refreshTokenUrl = new StringBuilder().append(WeChatManager.weChatAuthRefreshTokenPage).append(WeChatManager.getWECHATAPPID())
                        .append("&grant_type=").append("refresh_token").append("&refresh_token=").append(WeChatManager.REFRESH_ACCESS_TOKEN);
        Map<String, Object> refreshResult = HttpClientUtil.readHttpContentGet(refreshTokenUrl.toString());
        if (refreshResult != null && refreshResult.get(WeChatManager.ACCESS_TOKEN) != null) {
            logger.info("刷新获取access_token成功成功");
            return refreshResult;
        }
        return null;
    }

    /*=================== /private methond ======================*/
}
