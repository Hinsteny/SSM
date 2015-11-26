package com.hisoka.support.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.hisoka.support.config.Config;



/**
 * MailSender.java
 * 
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 * 
 */
@Component
public class MailSender {

    @Config("mail.user")
    private String mailUser;

    @Config("mail.password")
    private String mailPassword;

    @Config("mail.host")
    private String mailHost;

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }


    /**
     * 邮件发送
     * 
     * @param receiver 接收邮箱
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException
     */
    public void send(String receiver, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser, mailPassword);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailUser));
        String[] arr = receiver.split("，|,");
        InternetAddress[] address = new InternetAddress[arr.length];
        for (int i = 0; i < arr.length; i++) {
            address[i] = new InternetAddress(arr[i]);
        }
        msg.addRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(content, "text/html;charset=utf-8");
        Transport.send(msg);
    }

}
