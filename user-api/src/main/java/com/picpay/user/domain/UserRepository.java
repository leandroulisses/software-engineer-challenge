package com.picpay.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u left join u.relevance ur where LOWER(u.username)" +
            " like %:keyword% or LOWER(u.name) like %:keyword%")
    Page<User> findByKeyword(String keyword, Pageable pageable);

}
