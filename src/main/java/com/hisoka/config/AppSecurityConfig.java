package com.hisoka.config;

import com.hisoka.security.CustomUserDetailsService;
import com.hisoka.security.AppAuthenticationManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Hinsteny
 * @date 2016/7/28
 * @copyright: 2016 All rights reserved.
 */
//@Configuration
//@EnableWebSecurity
//public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf()
//                .disable()
//            .authorizeRequests()
//                .antMatchers("/static/**","/res/**", "/home", "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .logoutUrl("/")
//                .permitAll();
//    }
//
////    @Override
////    protected AuthenticationManager authenticationManager() throws Exception {
////        return super.getApplicationContext().getBean(AppAuthenticationManager.class);
////    }
//
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return super.getApplicationContext().getBean(CustomUserDetailsService.class);
//    }
//
//
//}
