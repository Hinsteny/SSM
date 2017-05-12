package org.hinsteny.repository;

import org.hinsteny.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends BaseRepos<User> {

    Long createUser(User user);

    Long updateUser(User user);

    Long deleteUser(User user);

}
