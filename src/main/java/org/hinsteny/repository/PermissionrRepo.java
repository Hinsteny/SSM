package org.hinsteny.repository;

import org.apache.ibatis.annotations.Param;
import org.hinsteny.bean.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionrRepo extends BaseRepos<Permission> {

    Permission findActorBypermName(@Param("name") String name);

    List<Permission> findRolesByRoleId(@Param("roleId") long roleId);
}
