package com.picpay;

import com.picpay.job.ImportJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private ImportJobService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
