package com.picpay.job.writer;

import com.picpay.user.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestItemWriter implements ItemWriter<User> {

    @Override
    public void write(List<? extends User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }

}