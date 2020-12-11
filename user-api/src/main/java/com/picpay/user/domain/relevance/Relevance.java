package com.picpay.user.domain.relevance;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Relevance {

    @Id
    @GeneratedValue
    private UUID id;

    private Integer rank;

    protected Relevance() {
        //to jpa
    }

    public UUID getId() {
        return id;
    }

    public Integer getRank() {
        return rank;
    }
}
