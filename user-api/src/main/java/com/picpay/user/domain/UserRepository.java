package com.picpay.user.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface UserRepository extends MongoRepository<User, UUID> {

    @Query("{ $text: { $search: ?0 } }")
    Page<User> findByKeyword(String keyword, Pageable pageable);

}
