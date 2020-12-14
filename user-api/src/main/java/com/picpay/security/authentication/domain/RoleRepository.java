package com.picpay.security.authentication.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends MongoRepository<Role, UUID> {

    List<Role> findByUserId(UUID userId);

}
