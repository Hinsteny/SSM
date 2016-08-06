package com.hisoka.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author Hinsteny
 * @date 2016/8/2
 * @copyright: 2016 All rights reserved.
 */
@Component
public class AppAuthenticationManager implements AuthenticationManager {

    @Value("#{new Boolean('${userPasswordEncrypt}')}")
    private boolean encrypt = false;


    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (!encrypt || auth.getName().equals(auth.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials());
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
