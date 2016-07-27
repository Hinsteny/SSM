package org.hinsteny.service.impl;

import org.hinsteny.bean.Good;
import org.hinsteny.bean.User;
import org.hinsteny.service.UserMongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserMongoServiceImpl implements UserMongoService {

    private Logger logger = LoggerFactory.getLogger(UserMongoServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static String USER_KEY = "d_Good";
	
	@Override
	public boolean create(Good good) {
		if (logger.isDebugEnabled()) logger.debug("Create one good for {}", good);
        mongoTemplate.insert(good, USER_KEY);
		return true;
	}

    @Override
    public Good find(Good good) {
        if (logger.isDebugEnabled()) logger.debug("Find one good with {}", good);
        Query query = Query.query(Criteria.where("goodName").is(good.getGoodName()));
        query.with(new Sort(Sort.Direction.ASC, "id"));
        return mongoTemplate.findOne(query, Good.class, USER_KEY);
    }

    @Override
    public List<Good> listUsers(Good good) {
        if (logger.isDebugEnabled()) logger.debug("Create users with {}", good);
        Query query = new Query().with(new Sort(Sort.Direction.ASC, "id"));
        return mongoTemplate.find(query, Good.class, USER_KEY);
    }

    @Override
    public void delete(Good good) {
        if (logger.isDebugEnabled()) logger.debug("Delete good with {}", good);
        Query query = Query.query(Criteria.where("goodName").is(good.getGoodName()));
        mongoTemplate.remove(query, User.class, USER_KEY);
    }
}
