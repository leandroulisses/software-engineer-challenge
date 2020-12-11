package com.picpay.user.domain.relevance;

import com.picpay.user.domain.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Relevance {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private RelevanceRankType rank;

    protected Relevance() {
        //to jpa
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public RelevanceRankType getRank() {
        return rank;
    }
    
}
