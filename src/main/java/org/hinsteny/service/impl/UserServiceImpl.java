package org.hinsteny.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.hinsteny.repository.UserRepository;
import org.hinsteny.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

	@Resource
    private UserRepository userRepository;
	
	@Override
	public boolean create(HashMap<String, Object> param) {
		System.out.println(param);
		userRepository.create(param);
		return true;
	}

}
