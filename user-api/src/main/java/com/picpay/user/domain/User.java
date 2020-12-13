package com.picpay.user.domain;

import com.picpay.user.domain.relevance.Relevance;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String username;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_ID")
    private List<Relevance> relevance;

    protected User() {
        //to jpa
    }

    public User(UUID id, String name, String username) {
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

    public List<Relevance> getRelevance() {
        return relevance;
    }

}
