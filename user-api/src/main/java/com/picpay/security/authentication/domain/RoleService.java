package com.picpay.security.authentication.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public interface RoleService {

    Collection<? extends GrantedAuthority> findByUserId(UUID userId);

}
