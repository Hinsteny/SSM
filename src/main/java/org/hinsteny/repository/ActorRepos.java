package org.hinsteny.repository;

import org.apache.ibatis.annotations.Param;
import org.hinsteny.bean.Actor;
import org.hinsteny.bean.Role;
import org.hinsteny.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepos extends BaseRepos<Actor> {

    Actor findActorByActorName(@Param("name") String name);

}
