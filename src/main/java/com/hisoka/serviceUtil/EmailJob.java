package com.hisoka.serviceUtil;

import com.hisoka.support.email.MailSender;
import com.hisoka.utils.StringUtil;
import org.hinsteny.bean.Email;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hinsteny
 * @date: 2015年11月25日
 * @copyright: 2015 All rights reserved.
 */
@Component
public class EmailJob {
    
    private static final String url = "tmpl/email.tmpl";
    
    @Resource
    private MailSender mailSender;

    public void send(Email email) throws MessagingException, IOException {

        //邮件模板
        String emailTemplate = StringUtil.getContentFromInputStream(new ClassPathResource(url).getInputStream(),"utf-8");

        Map<String,Object> data = new HashMap<>();
        data.put("totalRegister", 15);

//        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
//        Configuration cfg = Configuration.defaultConfiguration();
//        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
//        Template t = gt.getTemplate(emailTemplate);
//        t.binding(data);
//        mailSender.send(email.getToAddress(), email.getTitle(), t.render());
    }

}
