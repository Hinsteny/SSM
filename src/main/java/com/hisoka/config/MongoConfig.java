package com.hisoka.config;

import com.mongodb.Mongo;
import com.mongodb.MongoCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

/**
 * @author Hinsteny
 * @date 2016/7/27
 * @copyright: 2016 All rights reserved.
 */
@Configuration
public class MongoConfig {

    @Value("${mongo.host}")
    private  String host;

    @Value("#{new Integer('${mongo.port}')}")
    private  Integer port;

    @Value("${mongo.database}")
    private  String database;

    @Value("${mongo.username}")
    private  String username;

    @Value("${mongo.password}")
    private  String password;

    @Value("#{new java.text.SimpleDateFormat('${mongo.dateFormat}').parse('${mongo.dateStr}')}")
    Date date;

    public @Bean MongoClientFactoryBean mongoDbFactory() throws Exception {
        MongoClientFactoryBean clientFactoryBean = new MongoClientFactoryBean();
        clientFactoryBean.setHost(host);
        clientFactoryBean.setPort(port);
        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
        clientFactoryBean.setCredentials(new MongoCredential[]{credential});
        return clientFactoryBean;
    }

    public @Bean MongoTemplate mongoTemplate(Mongo mongo) throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, database);
        return mongoTemplate;

    }
}
