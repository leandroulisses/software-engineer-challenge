package com.picpay.security.authentication.domain;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserService {

    Optional<AuthUser> findById(UUID userId);

    Optional<AuthUser> findByUsername(String username);

}
