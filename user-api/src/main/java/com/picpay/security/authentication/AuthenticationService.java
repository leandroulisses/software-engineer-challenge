package com.picpay.security.authentication;

import com.picpay.security.authentication.domain.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private AuthUserService authUserService;

    @Autowired
    public AuthenticationService(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }
}
