package com.picpay.user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/db/create-user.sql")
    void should_find_ordered_by_relevance_rank() {
        Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "ur.rank", "name");
        PageRequest pageRequest = PageRequest.of(0, 15, sort);
        Page<User> page = userRepository.findByKeyword("leandro", pageRequest);
        List<User> users = page.getContent();
        Assertions.assertTrue(users.get(0).getName().equals("Lucas Silva"));
        Assertions.assertTrue(users.get(1).getName().equals("Leandro Passos"));
        Assertions.assertTrue(users.get(2).getName().equals("Leandro Ulisses"));
    }
}