package com.picpay.security.authentication.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private RoleName name;

    protected Role() {
    }
    
    @Override
    public String getAuthority() {
        return name.getValue();
    }

}
