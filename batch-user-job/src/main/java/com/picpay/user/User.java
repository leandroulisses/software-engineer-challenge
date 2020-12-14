package com.picpay.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class User {

    @Id
    private UUID id;
    private String name;
    private String username;
    private Integer rank;

    protected User() {
        //to jpa
    }

    public User(UUID id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.rank = Integer.MAX_VALUE;
    }

    public User(UUID id, String name, String username, Integer rank) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.rank = rank;
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

    public Integer getRank() {
        return rank;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
