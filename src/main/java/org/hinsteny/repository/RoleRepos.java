package org.hinsteny.repository;

import org.apache.ibatis.annotations.Param;
import org.hinsteny.bean.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepos extends BaseRepos<Role> {

    Role findActorByRoleName(@Param("name") String name);

    List<Role> findRolesByActorId(@Param("actorId") long actorId);
}
