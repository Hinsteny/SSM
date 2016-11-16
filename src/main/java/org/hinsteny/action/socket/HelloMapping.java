package org.hinsteny.action.socket;

import com.hisoka.utils.JsonUtil;
import com.hisoka.websocket.WebSocketManager;
import org.hinsteny.bean.socket.Greeting;
import org.hinsteny.bean.socket.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
@Controller("helloMapping")
public class HelloMapping {

    static final Logger logger = LoggerFactory.getLogger(WebSocketManager.class);

    @Autowired
    WebSocketManager webSocketManager;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(String greeting) {
        String text = "[" + LocalDateTime.now().toString() + "]:" + greeting;
        logger.info("Received greedting {}", text);
        return handler("key", text);
    }

    public Greeting handler(String trade, String principal) {
        logger.info("Send data key {}, value {}", trade, principal);
        // ...
        return new Greeting(principal);
    }

    @MessageExceptionHandler
    @SendToUser("/topic/exception")
    public String executeTrade(String event) {
        logger.info("Send data Exception {}", event);
        // ...
        return event;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public void broad(@RequestParam String greeting) {
        Greeting greetingO = new Greeting(greeting);
        Map<String, String> data = new HashMap<>();
        data.put("content", greeting);
        data.put("time", LocalDateTime.now().toString());
        logger.info("Received greedting {}", greeting);
        String dest = "/topic/greetings";
        //发送用户的聊天记录
        webSocketManager.broadcastMessage(dest, greetingO);
        dest = "/topic/work";
        //发送用户的聊天记录
        webSocketManager.broadcastMessage(dest, greetingO);
    }

}
