package com.picpay.user.dto;

import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String name;
    private String username;

    public UserDTO(UUID id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
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
