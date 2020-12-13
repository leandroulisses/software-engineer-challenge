package com.picpay.user.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateUserDTO {

    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
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
