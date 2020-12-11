package com.picpay.security.token;

import com.picpay.security.authentication.domain.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private static final String ROLE_CLAIM = "roles";

    @Value("${user.jwt.secret}")
    private String secret;

    @Value("${user.jwt.expiration}")
    private Long expiration;

    @Value("${user.jwt.issuer}")
    private String issuer;

    public String generateToken(Authentication authentication) {

        AuthUser loggedUser = (AuthUser) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + expiration);

        return Jwts.builder().setIssuer(issuer)
                .setSubject(loggedUser.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .claim(ROLE_CLAIM, loggedUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}
