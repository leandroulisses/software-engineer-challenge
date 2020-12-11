package com.picpay.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface UserRepository extends JpaRepository<User, UUID> {
}
