package org.hinsteny.action.socket;

import com.hisoka.rest.Get;
import com.hisoka.websocket.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
@Controller("helloHandler")
public class HelloHandler {

    static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);


    @Get("/testChat")
    public ModelAndView testChat(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Chat/index");
    }

    @Get("/testChatClient")
    public ModelAndView testChatClient(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Chat/index_client");
    }


}
