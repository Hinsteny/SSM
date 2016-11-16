package com.hisoka.websocket;

import com.hisoka.utils.JsonUtil;
import com.hisoka.utils.StringUtil;
import org.hinsteny.bean.socket.Greeting;
import org.hinsteny.bean.socket.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
@Component("webSocketManager")
public class WebSocketManager {

    static final Logger logger = LoggerFactory.getLogger(WebSocketManager.class);

    private volatile SimpMessagingTemplate template;

    @Autowired
    public WebSocketManager(SimpMessagingTemplate t) {
        template = t;
    }

    /**
     * Holder all client user sessions at here
     * @param String : sessionId
     */
    private ConcurrentHashMap<String, WebSocketClient> sessions = new ConcurrentHashMap<>();

    public void broadcastMessage(String destination, Object payload){
        String text = JsonUtil.writeEntityJSON(payload);
        this.template.convertAndSend(destination, text);
    }

    public boolean addSocketSession(String sessionId, WebSocketClient client){
        if (!StringUtil.isBlank(sessionId) && client != null){
            this.sessions.put(sessionId, client);
            return true;
        }
        return false;
    }

    /**
     *
     * @param sessionId
     * @return
     */
    public WebSocketClient removeSocketSession(String sessionId){
        Assert.notNull(sessionId, "sessionId should not be null!");
//        WebSocketClient client = null;
//        Set<Map.Entry<String, WebSocketClient>> entry = sessions.entrySet();
//        for (Map.Entry<String, WebSocketClient> item: entry) {
//            if (item.getKey().equals(sessionId)) {
//                client = item.getValue();
//                break;
//            }
//        }
        return sessions.remove(sessionId);
    }

    public boolean preHander(WebSocketSession session){
        Assert.notNull(session, "WebSocketSession should not be null!");
        boolean result = false;
        try {
            result = addSocketSession(session.getId(), renderClient(session));
        } catch (Exception e){
            logger.error("Render websocketclient error:{}", e.toString());
        }
        return result;
    }

    public boolean afterHander(WebSocketSession session){
        WebSocketClient client =  removeSocketSession(session.getId());
        return client != null;
    }

//    public boolean preHander( session){
//        Assert.notNull(session, "WebSocketSession should not be null!");
//        boolean result = false;
//        try {
//            result = addSocketSession(session.getId(), renderClient(session));
//        } catch (Exception e){
//            logger.error("Render websocketclient error:{}", e.toString());
//        }
//        return result;
//    }
//
//    public boolean afterHander(WebSocketSession session){
//        WebSocketClient client =  removeSocketSession(session.getId());
//        return client != null;
//    }

    public int broadcastMessage(final WebSocketMessage<?> message, final String sessionId){
        int count = 0;
        final boolean filter = StringUtil.isBlank(sessionId);
        sessions.forEach((key, value) -> {
            //广播到自身以外的client上去
            if (filter || key.equals(sessionId)){
                try {
                    logger.info("Send message to client {} with message {}", value.getClientIp(), message.toString());
                    value.getSocketSession().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return count;
    }

    private WebSocketClient renderClient(WebSocketSession session) throws Exception{
        //获取连接的唯一session id
        String sessionID = session.getId();
        //获取昵称
        String nickName = "";
        String path = URLDecoder.decode(session.getUri().getPath(),"UTF-8");
        String[] paths = path.split("\\/");
        for (int i=0; i<paths.length; ++i) {
            if (paths[i].equalsIgnoreCase("nickname")) {
                if (i < paths.length-1)
                    nickName = paths[i+1];
                break;
            }
        }
        logger.info("afterConnectionEstablished: {} -- {}", sessionID, nickName);
        //获取客户端ip地址
        String clientIp = session.getRemoteAddress().getAddress().getHostAddress();
        //将已连接的socket客户端保存
        WebSocketClient client = new WebSocketClient();
        client.setClientIp(clientIp);
        client.setNickName(nickName);
        client.setSocketSession(session);

        return client;
    }
}
