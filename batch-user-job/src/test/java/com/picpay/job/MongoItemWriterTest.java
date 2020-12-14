package com.picpay.job;

import com.picpay.config.MongoDbExtension;
import com.picpay.user.UserSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MongoDbExtension.class)
class MongoItemWriterTest {

    @Autowired
    private MongoItemWriter writer;

    @Test
    void should_write_users() throws Exception {
        UserSummary summary = new UserSummary();
        summary.setId(UUID.randomUUID());
        summary.setName("leandro");
        summary.setUsername("leandro.passos");
        List<UserSummary> users = List.of(summary);
        writer.write(users);
    }
}