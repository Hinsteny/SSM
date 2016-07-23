package org.hinsteny.service.impl;

import com.hisoka.cache.ApplicationEhcacheManager;
import com.hisoka.other.SessionContext;
import org.hinsteny.bean.User;
import org.hinsteny.repository.UserRepository;
import org.hinsteny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    ApplicationEhcacheManager ehcacheManager;
    
	@Resource
    private UserRepository userRepository;
	
	@Override
	public boolean create(User param) {
		if (logger.isDebugEnabled()) logger.debug("Create one user for {}", param);
		userRepository.save(param);
		return true;
	}

    @Override
    public User find(User user) {
        return userRepository.get(user);
    }

    @Override
    public List<User> listUsers(User param) {
        if (logger.isDebugEnabled()) logger.debug("Create users with {}", param);
        List<User> users = userRepository.query(param, null);
        return users;
    }

    @Override
    @Transactional
    public User doNotice(User user) {
        user = userRepository.get(user);
        if (user == null) throw new RuntimeException();
        user.setNoticeNum(user.getNoticeNum() + 1);
        if (logger.isDebugEnabled()) logger.debug("Increase users notice time to {}", user.getNoticeNum());
        userRepository.update(user);
        HttpServletRequest request = SessionContext.getRequest();
        String flag = request.getParameter("flag");
        if(flag != null && !(Integer.valueOf(flag) > 0)){
            if (logger.isDebugEnabled()) logger.debug("Occurred an error so just do roll back for user {} and noticeNum is {}", user.getUsername(), user.getNoticeNum());
            throw new RuntimeException();//终止操作, 查看前面的数据修改会进行事务回滚, noticeNum变为修改前的值
        }
        return user;
    }
}
