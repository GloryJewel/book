package com.gloryjewel.book.web.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void index() {
        String body = restTemplate.getForObject("/", String.class);
        System.out.println(body);
        assertTrue(body.contains("스프링 부트로"));
    }
}