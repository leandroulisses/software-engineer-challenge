package com.picpay.user.dto;

import java.util.UUID;

public class CreateUserDTO {

    private UUID id;
    private String name;
    private String username;

    protected CreateUserDTO() {
        //to deserialize
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
