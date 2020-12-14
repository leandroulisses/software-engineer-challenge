package com.picpay.config;

import com.picpay.security.authentication.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MongoMigration implements CommandLineRunner {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void changeUser() {
        AuthUser authUser = new AuthUser(UUID.fromString("ec5f66aa-46e4-4f11-9751-f2e2bf285f2c"),
                "user", "12345");
        authUserRepository.save(authUser);
        changeRole(authUser.getId());
    }

    public void changeRole(UUID userId) {
        Role role = new Role(UUID.fromString("ec5f66aa-46e4-4f11-9751-f2e2bf285f3c"), RoleNameConstants.USER, userId);
        roleRepository.save(role);
    }

    @Override
    public void run(String... args) throws Exception {
        changeUser();
    }

}
