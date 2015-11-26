package com.hisoka.applicationEvent;

import org.springframework.context.ApplicationEvent;

import com.hisoka.POJO.Email;


/**
 * EmailMonitorEvent.java
 * 
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 * 
 */
public class EmailMonitorEvent extends ApplicationEvent {

    /**
     * serialVersionUID
     * long
     */
    private static final long serialVersionUID = 7197120866146492975L;
    
    private final String sendAddress;
    private final Email email;

    public EmailMonitorEvent(Object source, String sendAddress, Email email) {
        super(source);
        this.sendAddress = sendAddress;
        this.email = email;
    }

    public String getSendAddress() {
        return sendAddress;
    }
    
    public Email getEmail() {
        return email;
    }

}
