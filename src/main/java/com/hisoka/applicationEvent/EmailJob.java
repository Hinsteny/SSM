package com.hisoka.applicationEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.hisoka.POJO.Email;
import com.hisoka.support.email.MailSender;
import com.hisoka.utils.Strings;

/**
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 */
@Component
public class EmailJob {
    
    private static final String url = "email.tmpl";
    
    @Resource
    private MailSender mailSender;

    public void send(Email email) throws MessagingException, IOException {

        //邮件模板
        String emailTemplate = Strings.getContentFromInputStream(new ClassPathResource(url).getInputStream(),"utf-8");

        Map<String,Object> data = new HashMap<>();
        data.put("totalRegister", 15);

        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate(emailTemplate);
        t.binding(data);
        mailSender.send(email.getToAddress(), email.getTitle(), t.render());
    }

}
