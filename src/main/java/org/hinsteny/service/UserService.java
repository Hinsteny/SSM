package org.hinsteny.service;

import org.hinsteny.bean.User;

public interface UserService extends BaseService<User>{

    User doNotice(User user);


}
