package com.picpay.user;

import java.util.UUID;

public class UserRankOne {

    private UUID id = UUID.randomUUID();
    private UUID userId;
    private Integer rank = 1;

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

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}