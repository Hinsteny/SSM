package org.hinsteny.event.service;

import com.hisoka.support.email.MailSender;
import org.hinsteny.bean.Email;
import org.hinsteny.event.bean.EmailMonitorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;


/**
 * EmailService.java
 * 
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 * 
 */
@Component
public class EmailService implements ApplicationEventPublisherAware {

    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    MailSender mailSender;
    
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void sendEmail(String toAddress, String title, String content) {
        Email email = new Email();
        email.setTitle(title);
        email.setContent(content);
        email.setToAddress(toAddress);
        EmailMonitorEvent event = new EmailMonitorEvent(this, mailSender.getMailUser(), email);
        logger.debug("publish send email Event!");
        publisher.publishEvent(event);
        // other service
        
    }

}
