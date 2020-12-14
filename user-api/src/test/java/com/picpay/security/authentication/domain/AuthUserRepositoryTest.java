package com.picpay.security.authentication.domain;

import com.picpay.config.MongoDbExtension;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ExtendWith(MongoDbExtension.class)
class AuthUserRepositoryTest {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Resource validUser = new ClassPathResource("db/auth-users.json");

    @Test
    void should_find_by_username() throws IOException {
        String jsonString = FileUtils.readFileToString(validUser.getFile(), "UTF-8");
        Document document = Document.parse(jsonString);
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        mongoTemplate.getCollection("authuser").insertMany(documents);

        Optional<AuthUser> authUser = authUserRepository.findByUsername("admin");
        System.out.println(authUser.get());
    }

}