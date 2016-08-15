package com.hisoka.security;

import org.hinsteny.bean.Actor;
import org.hinsteny.bean.Permission;
import org.hinsteny.bean.Role;
import org.hinsteny.repository.ActorRepos;
import org.hinsteny.repository.PermissionrRepo;
import org.hinsteny.repository.RoleRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Hinsteny
 * @date 2016/8/9
 * @copyright: 2016 All rights reserved.
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private ActorRepos actorRepos;

    @Autowired
    private RoleRepos roleRepos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Actor actor = actorRepos.findActorByActorName(username);
            // check that the username exists
            if (actor == null) {
                throw new UsernameNotFoundException("Could not find username: " + username + " in the DB.");
            }
            // adapt as needed
            return new CustomUserDetails(actor.getName(), actor.getPassword(), getAuthorities(actor));
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
            throw new UsernameNotFoundException(username + " not found", e);
        }
    }

    public static class CustomUserDetails implements UserDetails{

        private String username;
        private String password;
        private boolean enabled = true;
        private boolean accountNonExpired = true;
        private boolean credentialsNonExpired = true;
        private boolean accountNonLocked = true;
        Collection<? extends GrantedAuthority> authorities;

        public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }
    }

    /**
     * Retrieves a collection of {@link GrantedAuthority} based on a list of
     * roles
     *
     * @param actor the assigned roles of the user
     * @return a collection of {@link GrantedAuthority}
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Actor actor) {
        Collection<Role> roles = roleRepos.findRolesByActorId(actor.getActorId());
        Set<SimpleGrantedAuthority> authList = new TreeSet(new SimpleGrantedAuthorityComparator());
        if (roles.size() > 0)
            roles.stream().forEach(role -> authList.add(new SimpleGrantedAuthority(role.getName())));
        return authList;
    }

//    /**
//     * Wraps a {@link Role} role to {@link SimpleGrantedAuthority} objects
//     *
//     * @param role
//     * @return list of granted authorities
//     */
//    public Set<SimpleGrantedAuthority> getGrantedAuthorities(Role role) {
//        Set<SimpleGrantedAuthority> authorities = new HashSet();
//        if (isWeb){
//            Collection<Permission> permissions = permissionrRepo.findRolesByRoleId(role.getRoleId());
//            permissions.stream().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
//            /*for (Permission permission : permissions) {
//                authorities.add(new SimpleGrantedAuthority(permission.getName()));
//            }*/
//        }
//        else {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }

    private static class SimpleGrantedAuthorityComparator implements Comparator<SimpleGrantedAuthority> {

        @Override
        public int compare(SimpleGrantedAuthority o1, SimpleGrantedAuthority o2) {
            return o1.equals(o2) ? 0 : -1;
        }
    }

}
