package com.picpay.security.authentication.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends MongoRepository<AuthUser, UUID> {

    Optional<AuthUser> findByUsername(String username);

}
