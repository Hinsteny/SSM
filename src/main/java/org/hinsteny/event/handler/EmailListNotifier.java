package org.hinsteny.event.handler;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.hinsteny.event.bean.EmailMonitorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.hisoka.serviceUtil.EmailJob;

/**
 * EmailListNotifier.java
 * 
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 * 
 */
@Component
public class EmailListNotifier implements ApplicationListener<EmailMonitorEvent> {

    private Logger logger = LoggerFactory.getLogger(EmailListNotifier.class);
    
    @Resource
    private EmailJob emailJob;
    
    @Override
    public void onApplicationEvent(EmailMonitorEvent event) {
        try {
            logger.debug("Just do send email service!");
            emailJob.send(event.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
