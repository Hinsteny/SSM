package org.hinsteny.service.impl;

import org.hinsteny.bean.User;
import org.hinsteny.service.UserRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRedisServiceImpl implements UserRedisService {

    private Logger logger = LoggerFactory.getLogger(UserRedisServiceImpl.class);

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    private static String USER_KEY = "User";
	
	@Override
	public boolean create(User user) {
		if (logger.isDebugEnabled()) logger.debug("Create one user for {}", user);
        this.redisTemplate.opsForHash().put(USER_KEY, user.getUsername(), user);
		return true;
	}

    @Override
    public User find(User user) {
        return (User)this.redisTemplate.opsForHash().get(USER_KEY, user.getUsername());
    }

    @Override
    public List<User> listUsers(User user) {
        if (logger.isDebugEnabled()) logger.debug("Create users with {}", user);
        List<Object> users = this.redisTemplate.opsForHash().values(USER_KEY);
        return null;
    }

    @Override
    public void delete(User user) {
        this.redisTemplate.opsForHash().delete(USER_KEY, user.getUsername());
    }
}
