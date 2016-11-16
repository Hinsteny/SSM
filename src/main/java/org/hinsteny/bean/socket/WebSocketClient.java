package org.hinsteny.bean.socket;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/11/11
 * @copyright: 2016 All rights reserved.
 */
public class WebSocketClient {

    private WebSocketSession socketSession;

    private String nickName;
    private String clientIp;

    public WebSocketSession getSocketSession() {
        return socketSession;
    }

    public void setSocketSession(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
