package com.hisoka.Component;

import com.hisoka.support.config.Config;
import com.hisoka.utils.DateTimeUtil;
import com.hisoka.utils.HttpClientUtil;
import com.hisoka.utils.StringUtil;
import com.hisoka.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 管理微信开发，授权信息获取及保存
 *
 * @author Hinsteny
 * @date 2016/1/13
 * @copyright: 2016 All rights reserved.
 */
@Component
public class WeChatManager implements InitializingBean {

    @Config("wechat.appid")
    private static String WECHATAPPID;

    @Config("wechat.appsecret")
    private static String WECHATAPPSECRET;

    @Config("wechat.key")
    private static String WECHATKEY;

    @Config("wechat.mchid")
    private static String WECHATMCHID;

    @Config("wechat.sdbmchid")
    private static String WECHATSDBMCHID;

    public static final String WECHATOPENID = "wechat_openid";

    public static final String OPENID = "openid";

    public static final String STATE = "state";

    public static final String CODE = "code";

    public static final String ERRMSG = "errmsg";

    public static final String OK = "ok";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String ACCESS_TOKEN_TIME = "access_token_time";

    public final static String WECHATAUTHSCOPE_BASE = "snsapi_base";

    public final static String WECHATAUTHSCOPE_USERINFO = "snsapi_userinfo";

    public static final String WECHAT_OAUTH_STATE = "something";

    public static final String REFRESH_ACCESS_TOKEN = "refresh_token";

    public static final String JSAPI_TICKET = "ticket";

    public static final String JSAPI_TICKET_TIME = "ticket_time";

    // 微信官方给的access_token过期时间为7200秒，但是由于代码层面需要拿到过期时间再比较，再请求，所以这里稍微把过期时间设置小一点
    public static final int WECHAT_ACCESSTOKEN_EXPIRETIME = 7000;

    // Get:https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
    public final static String WECHATACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";

    public final static String WECHATAUTHURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";

    public final static String WECHATJSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

    public final static String WECHATDOWNLOADMEDIAURL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";

    public final static String WECHATUSERLISTURL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

    // Get:https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
    public final static String WECHATUSERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";

    //Post:https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN  Data:{"user_list": [ { "openid": "otvxTs4dckWG7imySrJd6jSi0CWE", "lang": "zh-CN" }, { "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg", "lang": "zh-CN"} ] }
    public final static String WECHATUSERLISTINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";

    // look into: http://mp.weixin.qq.com/wiki/7/1c97470084b73f8e224fe6d9bab1625b.html
    public final static String WECHATJSAPILIST = "[\'onMenuShareTimeline\',\'onMenuShareAppMessage\',\'chooseImage\',\'previewImage\', \'uploadImage\']";

    /*==============================================  wechat page data ====================================================*/
    public final static String WECHATUSERINFOPAGE = "https://api.weixin.qq.com/sns/userinfo?access_token=";

    public final static String weChatAuthAccessTokenPage = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";

    public final static String weChatAuthRefreshTokenPage = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=";

    public final static String weChatAuthCheckTokenPage = "https://api.weixin.qq.com/sns/auth?access_token=";

    /*==============================================  /wechat page data ====================================================*/

    // 缓存: 存储一些微信授权会用到的信息
    private static volatile Map<String, Object> wechatData = new HashMap<>();

    static final Logger logger = LoggerFactory.getLogger(WeChatManager.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化，获取微信相关数据(这里可以修改为延迟初始化)
//        initializeWeChatAPPAccessToken(true);
//        initializeWeChatAPPJsapiTicket(true);
    }

    /**
     * 初始化服务所需的APP AccessToken
     * @param isInitialize
     */
    private void initializeWeChatAPPAccessToken(boolean isInitialize){
        if (isInitialize || wechatData.get(ACCESS_TOKEN) == null) {
            // 啊，缓存里面还没有accesstoken，那去获取吧
            logger.debug("Can't get accesstoken, so go to access the new accesstoken!");
            Map accessToken = getWechatAPPAccessTokenFromWeChat();
            if (accessToken != null) {
                Date expireTime = DateTimeUtil.addTime(new Date(), 13, WECHAT_ACCESSTOKEN_EXPIRETIME);
                wechatData.put(ACCESS_TOKEN, accessToken);
                wechatData.put(ACCESS_TOKEN_TIME, expireTime);
                logger.info("获取APP-access_token成功，这里把数据添加到缓存，并添加过期时间");
            }else{
                //延迟函数再去请求下。。。
            }
        } else {
            //缓存里已经有了数据，我们可以再去验证一下accesstoken的有效性呗

        }
    }

    /**
     * 初始化服务所需的APP Jsapi Ticket
     * @param isInitialize
     */
    private void initializeWeChatAPPJsapiTicket(boolean isInitialize){
        if (isInitialize || wechatData.get(JSAPI_TICKET) == null) {
            // 啊，缓存里面还没有accesstoken，那去获取吧
            logger.debug("Can't get jsapiTicket, so go to access the new jsapiTicket!");
            Map jsapiTicket = getWeChatJsapiTicketFromWeChat(getWechatAPPAccessToken());
            if (jsapiTicket != null) {
                Date expireTime = DateTimeUtil.addTime(new Date(), 13, WECHAT_ACCESSTOKEN_EXPIRETIME);
                wechatData.put(JSAPI_TICKET, jsapiTicket);
                wechatData.put(ACCESS_TOKEN_TIME, expireTime);
                logger.info("获取APP-access_token成功，这里把数据添加到缓存，并添加过期时间");
            }else{
                //延迟函数再去请求下。。。
            }
        } else {
            //缓存里已经有了数据，我们可以再去验证一下accesstoken的有效性呗

        }
    }
    /**
     * 内部服务获取 APP access_token
     *
     * @return 获取到有效的凭据
     */
    public String getWechatAPPAccessToken() {
        Map<String, Object> accessToken;
        if (wechatData.get(ACCESS_TOKEN) != null) {
            if (wechatData.get(ACCESS_TOKEN_TIME) != null) {
                Date expireTime = (Date) wechatData.get(ACCESS_TOKEN_TIME);
                if (expireTime.before(new Date())) {
                    // 这里表示access_token已经过期，因此我们从新获取accesstoken
                    logger.info("The expireTime of accesstoken has expired!");
                    initializeWeChatAPPAccessToken(false);
                }
            } else {
                // 这里表示竟然没有获取到过期时间，不管了，直接从新获取accesstoken
                logger.info("Can't get expireTime of accesstoken, so go to access the new accesstoken!");
                initializeWeChatAPPAccessToken(false);
            }
        } else {
            // 啊，缓存里面还没有accesstoken，那去获取吧
            logger.info("Can't get accesstoken, so go to access the new accesstoken!");
            initializeWeChatAPPAccessToken(false);
        }
        // 终于可以获取accesstoken
        accessToken = (Map) wechatData.get(ACCESS_TOKEN);
        return (String) accessToken.get(ACCESS_TOKEN);
    }

    /**
     * 根据access_token获取访问微信js接口的临时票据 微信端返回结果 : { "errcode":0, "errmsg":"ok", "ticket":
     * "bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA" ,
     * "expires_in":7200 }
     *
     * @return 执行成功返回对应的
     */
    public String getWeChatJsapiTicket() {
        Map<String, Object> accessToken = null;
        if (wechatData.get(JSAPI_TICKET) != null) {
            if (wechatData.get(JSAPI_TICKET_TIME) != null) {
                Date expireTime = (Date) wechatData.get(JSAPI_TICKET_TIME);
                if (expireTime.before(new Date())) {
                    // 这里表示jsapi_ticket已经过期，因此我们从新获取jsapiticket
                    logger.info("The expireTime of accesstoken has expired!");
                    initializeWeChatAPPJsapiTicket(true);
                }
            } else {
                // 这里表示竟然没有获取到过期时间，不管了，直接从新获取jsapiticket
                logger.info("Can't get expireTime of accesstoken, so go to access the new accesstoken!");
                initializeWeChatAPPJsapiTicket(true);
            }
        } else {
            // 啊，缓存里面还没有 jsapiticket，那去获取吧
            logger.info("Can't get accesstoken, so go to access the new accesstoken!");
            initializeWeChatAPPJsapiTicket(true);
        }
        // 终于可以获取 jsapiticket
        accessToken = (Map<String, Object>) wechatData.get(JSAPI_TICKET);
        return (String) accessToken.get(JSAPI_TICKET);
    }

    /**
     * <p>
     * 成功: {"access_token":"ACCESS_TOKEN","expires_in":7200}
     * </p>
     * <p>
     * 失败: {"errcode":40013,"errmsg":"invalid appid"}
     * </p>
     * 请求微信公众号的access_token access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
     * access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新， 重复获取将导致上次获取的access_token失效。
     *
     * @return 如果没有获取到有效的凭据，就返回 null
     */
    private static Map getWechatAPPAccessTokenFromWeChat() {
        StringBuilder accessTokenUrl = new StringBuilder().append(WECHATACCESSTOKEN).append(WECHATAPPID)
                        .append("&secret=").append(WECHATAPPSECRET);
        Map result = HttpClientUtil.readHttpContentGet(accessTokenUrl.toString());
        if (result != null && result.get(ACCESS_TOKEN) != null)
            return result;
        return null;
    }

    /**
     * 从微信服务器获取jsapi_ticket:[(jsapi_ticket是公众号用于调用微信JS接口的临时票据（有效期为7200秒，通过access_token来获取）]
     *
     * @param access_token
     * @return
     */
    private Map getWeChatJsapiTicketFromWeChat(String access_token) {
        StringBuilder getJsapiTicketUrl = new StringBuilder().append(WECHATJSAPITICKET).append(access_token).append("&type=jsapi");
        Map result = HttpClientUtil.readHttpContentGet(getJsapiTicketUrl.toString());
        if (result != null && "ok".equals(result.get("errmsg")))
            return result;
        return null;
    }

    /**
     * 包装微信网页授权的地址，授权方式为: 静默类型(用户不可感知,授权完成后自动跳转到回调的网页)
     * @param redirectUri
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public static String getWechatUserAuthorizeSilentUrl(String redirectUri) throws IOException,
            ServletException {
        logger.info("Ask WechatUser silent authorize then redirect to :{}", redirectUri);
        StringBuilder url = new StringBuilder().append(WECHATAUTHURL).append(WeChatManager.getWECHATAPPID())
                        .append("&redirect_uri=")
                        .append(URLEncoder.encode(redirectUri, WebUtil.DEFAULT_URL_CHARSET))
                        .append("&response_type=code").append("&scope=")
                        .append(WECHATAUTHSCOPE_BASE).append("&state=")
                        .append(WECHAT_OAUTH_STATE).append("#wechat_redirect");
        return url.toString();
    }

    /**
     * 包装微信网页授权的地址，授权方式为: 感知类型(需要用户点击授权按钮,授权完成后自动跳转到回调的网页)
     * @param redirectUri
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public static String getWechatUserAuthorizeSenseUrl(String redirectUri) throws IOException,
            ServletException {
        logger.info("Ask WechatUser sense authorize then redirect to :{}", redirectUri);
        StringBuilder url = new StringBuilder().append(WECHATAUTHURL).append(WeChatManager.getWECHATAPPID())
                        .append("&redirect_uri=")
                        .append(URLEncoder.encode(redirectUri, WebUtil.DEFAULT_URL_CHARSET))
                        .append("&response_type=code").append("&scope=")
                        .append(WECHATAUTHSCOPE_USERINFO).append("&state=")
                        .append(WECHAT_OAUTH_STATE).append("#wechat_redirect");
        return url.toString();
    }

    /**
     * 获取微信的openid
     *
     * @param stateCode
     * @return
     */
    public static String getWechatOpenId(String stateCode) {
        // log.debug("来自微信的访问,开始请求oauth openid");
        try {
            URL url = new URL("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                            + WeChatManager.getWECHATAPPID() + "&secret="
                            + WeChatManager.getWECHATAPPSECRET() + "&code="
                            + stateCode + "&grant_type=authorization_code");
            String result = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }

            // log.debug("wechat oauth json:" + result);

            Matcher m = Pattern.compile("\"openid\":\"([^\"]+)\"").matcher(
                    result);
            if (m.find()) {
                logger.info("wechat oauth openID:" + m.group(1));
                return m.group(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取微信分享时用的签名数据
     * @param request
     * @return
     */
    public static Map<String, String>  getWechatShareSign(HttpServletRequest request, String jsapiTicket){
        String requestUrl = WebUtil.getRequsetUrl(request, true);
        Map<String, String> sign = sign(jsapiTicket, requestUrl);
        return sign;
    }

    /**
     * <p>
     * 参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
     * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
     * </p>
     *
     * 根据jsapi_ticket生成调用微信jsdk的签名
     *
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> result = new HashMap<>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String signStr;
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        signStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        logger.info("{}", signStr);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(signStr.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        result.put("url", url);
        result.put("jsapi_ticket", jsapi_ticket);
        result.put("appId", WeChatManager.getWECHATAPPID());
        result.put("timestamp", timestamp);
        result.put("nonceStr", nonce_str);
        result.put("signature", signature);
        result.put("jsApiList", WECHATJSAPILIST);
        return result;
    }

    /**
     * 根据传入的mediaId返回对应的获取微信资源的url (微信提供的资源下载接口)
     * @param mediaId
     * @return
     */
    public static String getWexinMedia(String mediaId, String accessToken) {
        StringBuilder url = new StringBuilder(WECHATDOWNLOADMEDIAURL).append(accessToken).append("&media_id=").append(mediaId);
        return url.toString();
    }

    /**
     *<p>
     * 获取成功:{
     *  "total":23000,
     *  "count":10000,
     *  "data":{"
     *    openid":[
     *      "OPENID1",
     *      "OPENID2",
     *      ...,
     *      "OPENID10000"
     *    ]
     *  },
     *  "next_openid":"OPENID10000"
     *  }
     *</p>
     *
     *<p>
     * 获取失败:{"errcode":40013,"errmsg":"invalid appid"}
     *</p>
     *
     *
     * 获取微信公众帐号的关注者列表(wechat user openID),每次最多10000个
     *
     * @param accessToken
     * @param next_openid: 没有此参数，则默认从关注列表的第一个开始获取
     * @return
     */
    public static Map getWechatUserOpenIDList(String accessToken, String next_openid) {
        StringBuilder urlB = new StringBuilder().append(WECHATUSERLISTURL).append(accessToken);
        if (!StringUtil.isBlank(next_openid))
            urlB.append("&next_openid=").append(next_openid);
        Map result = HttpClientUtil.readHttpContentGet(urlB.toString());
        if (result != null && result.get(ERRMSG) == null)
            return result;
        return null;
    }

    /**
     *<p>
     * 获取成功:{
     *    subscribe": 1,
     *    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
     *    "nickname": "Band",
     *    "sex": 1,
     *    "language": "zh_CN",
     *    "city": "广州",
     *    "province": "广东",
     *    "country": "中国",
     *    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     *    "subscribe_time": 1382694957,
     *    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     *    "remark": "",
     *    "groupid": 0
     *  }
     *</p>
     *
     *<p>
     * 获取失败:{"errcode":40013,"errmsg":"invalid appid"}
     *</p>
     *
     *
     * 获取微信公众帐号的关注者列表(wechat user openID),每次最多10000个
     *
     * @param accessToken
     * @param next_openid: 没有此参数，则默认从关注列表的第一个开始获取
     * @return
     */
    public static Map getWechatUserInfo(String accessToken, String openid) {
        StringBuilder urlInfo = new StringBuilder().append(WECHATUSERINFO).append(accessToken)
                        .append("&openid=").append(openid).append("&lang=zh_CN");
        Map result = HttpClientUtil.readHttpContentGet(urlInfo.toString());
        if (result != null && result.get(ERRMSG) == null)
            return result;
        return null;
    }

    /*=================== private methond ======================*/
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    /*=================== /private methond ======================*/

    public static String getWECHATAPPID() {
        return WECHATAPPID;
    }

    public static void setWECHATAPPID(String WECHATAPPID) {
        WeChatManager.WECHATAPPID = WECHATAPPID;
    }

    public static String getWECHATAPPSECRET() {
        return WECHATAPPSECRET;
    }

    public static void setWECHATAPPSECRET(String WECHATAPPSECRET) {
        WeChatManager.WECHATAPPSECRET = WECHATAPPSECRET;
    }
}
