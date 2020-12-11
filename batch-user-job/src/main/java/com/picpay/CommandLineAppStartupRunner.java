package com.picpay;

import com.picpay.job.ImportJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private ImportJobService jobService;

    private static final String URL_BASE = "https://s3.amazonaws.com/careers-picpay/users.csv.gz";
    private static final String FILE_NAME = "base.csv";

    @Override
    public void run(String... args) throws Exception {
       jobService.execute(FILE_NAME, URL_BASE);
    }
}