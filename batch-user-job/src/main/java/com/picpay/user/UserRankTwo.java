package com.picpay.user;

import java.util.UUID;

public class UserRankTwo {

    private UUID id = UUID.randomUUID();
    private UUID userId;
    private Integer rank = 2;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
