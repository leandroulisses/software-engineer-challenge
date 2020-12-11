package com.picpay.user.domain.relevance;

public enum RelevanceRankType {

    LIST_ONE(2), LIST_TWO(1);

    Integer value;

    RelevanceRankType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
