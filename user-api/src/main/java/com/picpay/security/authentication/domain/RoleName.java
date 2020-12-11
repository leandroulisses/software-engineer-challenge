package com.picpay.security.authentication.domain;

public enum RoleName {

    INTEGRATION(RoleNameConstants.INTEGRATION),
    USER(RoleNameConstants.USER);

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
