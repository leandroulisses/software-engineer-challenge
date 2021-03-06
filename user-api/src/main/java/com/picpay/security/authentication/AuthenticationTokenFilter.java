package com.picpay.security.authentication;

import com.picpay.security.token.TokenService;
import com.picpay.security.authentication.domain.AuthUser;
import com.picpay.security.authentication.domain.AuthUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthenticationTokenFilter extends GenericFilterBean {

    private static final int BEGIN_INDEX = 7;
    private static final String ROLE_PREFIX = "ROLE_";
    private TokenService tokenService;
    private AuthUserService authUserService;

    public AuthenticationTokenFilter(TokenService tokenService, AuthUserService authUserService) {
        this.tokenService = tokenService;
        this.authUserService = authUserService;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(BEGIN_INDEX);
    }

    private void authenticateClient(String token) {
        UUID userId = tokenService.getUserId(token);
        AuthUser user = authUserService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, toRoleAuthority(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> toRoleAuthority(AuthUser user) {
        return user.getAuthorities().stream()
                .map(grantedAuthority ->
                        new SimpleGrantedAuthority(ROLE_PREFIX.concat(grantedAuthority.getAuthority())))
                .collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = getToken((HttpServletRequest) servletRequest);
        boolean validToken = tokenService.isTokenValid(token);
        if (validToken) {
            authenticateClient(token);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
