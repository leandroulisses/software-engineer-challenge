package com.picpay.job;

import com.picpay.user.User;
import com.picpay.user.UserRepository;
import com.picpay.user.UserSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoItemWriter implements ItemWriter<UserSummary> {
    private static final Logger LOG = LoggerFactory.getLogger(MongoItemWriter.class);

    private final UserRepository userRepository;

    public MongoItemWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(List<? extends UserSummary> users) throws Exception {
        LOG.info("Console item writer starts");
        List<User> userList = users.stream()
                .map(userSummary -> new User(userSummary.getId(), userSummary.getName(), userSummary.getUsername()))
                .collect(Collectors.toList());
        userRepository.saveAll(userList);
        LOG.info("Console item writer ends");
    }

}
