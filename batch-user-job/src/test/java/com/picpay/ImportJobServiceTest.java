package com.picpay;

import com.picpay.job.ImportJobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ImportJobServiceTest {

    private static final String URL_BASE = "https://s3.amazonaws.com/careers-picpay/users.csv.gz";
    private static final String FILE_NAME = "base.csv";

    @Autowired
    private ImportJobService jobService;


    @Test
    void should_execute_job() throws IOException {
        jobService.execute(FILE_NAME, URL_BASE);
    }
}