package com.picpay.security.authentication.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Document
public class Role implements GrantedAuthority {

    @Id
    private UUID id;

    private String name;

    private UUID userId;

    protected Role() {
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }
}
