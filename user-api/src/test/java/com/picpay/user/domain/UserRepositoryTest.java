package com.picpay.user.domain;

import com.picpay.config.MongoDbExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

@DataMongoTest
@ExtendWith(MongoDbExtension.class)
class UserRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_find_ordered_by_relevance_rank() {
        loadData();
        Sort sort = Sort.by(Sort.Direction.ASC, "rank", "name");
        PageRequest pageRequest = PageRequest.of(0, 15, sort);
        Page<User> page = userRepository.findByKeyword("leandro", pageRequest);
        List<User> users = page.getContent();
        Assertions.assertTrue(users.get(0).getName().equals("Lucas Silva"));
        Assertions.assertTrue(users.get(1).getName().equals("Leandro Passos"));
        Assertions.assertTrue(users.get(2).getName().equals("Leandro Ulisses"));
    }

    private void loadData() {
        User user = new User(UUID.randomUUID(), "Lucas Silva", "leandro.silva", 1);
        User userTwo = new User(UUID.randomUUID(), "Leandro Passos", "leandro.passos", 2);
        User userThree = new User(UUID.randomUUID(), "Leandro Ulisses", "leandro.ulisses");
        mongoTemplate.insert(user);
        mongoTemplate.insert(userTwo);
        mongoTemplate.insert(userThree);
    }
}