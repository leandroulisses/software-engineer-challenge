package com.picpay.security.authentication;

import com.picpay.security.authentication.dto.LoginFormDTO;
import com.picpay.security.authentication.dto.TokenDTO;
import com.picpay.security.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginFormDTO form) {
        UsernamePasswordAuthenticationToken loginData = form.converter(form);
        try {
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            LOG.info("User authenticated: {}", form.getUsername());
            return ResponseEntity.ok(TokenDTO.newBuilder()
                    .withToken(token)
                    .withType("Bearer")
                    .build());
        } catch (AuthenticationException e) {
            LOG.error("Error authenticating user: {} {}", form.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
