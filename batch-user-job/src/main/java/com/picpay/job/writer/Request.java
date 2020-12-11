package com.picpay.job.writer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Request {

    private final RestTemplate restTemplate = new RestTemplate();


}
