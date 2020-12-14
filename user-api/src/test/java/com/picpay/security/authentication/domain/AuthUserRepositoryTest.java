package com.picpay.security.authentication.domain;

import com.picpay.config.MongoDbExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@DataMongoTest
@ExtendWith(MongoDbExtension.class)
class AuthUserRepositoryTest {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void should_find_by_username() throws IOException {
        migrateUser();
        Optional<AuthUser> authUser = authUserRepository.findByUsername("admin");
        Assertions.assertTrue(authUser.get().getUsername().equals("admin"));
    }

    void migrateUser() {
        AuthUser authUser = new AuthUser(UUID.fromString("ec5f66aa-46e4-4f11-9751-f2e2bf285f2c"),
                "admin", "12345");
        mongoTemplate.insert(authUser);
    }

}