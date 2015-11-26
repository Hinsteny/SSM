package org.hinsteny.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hinsteny.repository.UserRepository;
import org.hinsteny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hisoka.cache.ApplicationEhcacheManager;


@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    ApplicationEhcacheManager ehcacheManager;
    
	@Resource
    private UserRepository userRepository;
	
	@Override
	public boolean create(HashMap<String, Object> param) {
		System.out.println(param);
		userRepository.create(param);
		return true;
	}

    @Override
    public List<HashMap<String, Object>> listUsers(HashMap<String, Object> param) {
        List<HashMap<String, Object>> users = userRepository.list(param);
        return users;
    }

}
