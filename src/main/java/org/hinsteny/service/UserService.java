package org.hinsteny.service;

import org.hinsteny.bean.User;

public interface UserService extends BaseService<User>{

    boolean login(User user);

    boolean logout(User user);

    User doNotice(User user);

    Long createUser(User user);

    Long updateUser(User user);

    Long deleteUser(User user);
}
