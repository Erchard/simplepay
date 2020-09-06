package org.simplepay.paysystem.login.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.matchers.Null;
import org.simplepay.paysystem.login.model.ApplicationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URLDecoder;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    Gson gson;

    private TestRestTemplate restTemplate = new TestRestTemplate();


    static String token;

    @Test
    @Order(1)
    void testSignUp() {
        ApplicationUser user = new ApplicationUser();
        user.setUsername("admin");
        user.setPassword("qwerty");
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8080/users/sign-up", user, String.class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);


    }

    @Test
    @Order(2)
    void testLogIn() {
        ApplicationUser user = new ApplicationUser();
        user.setUsername("admin");
        user.setPassword("qwerty");
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8080/login", user, String.class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);

        token = result.getHeaders().get("Authorization").get(0);
        logger.info("Header: {}", token);
    }

    @Test
    @Order(3)
    void testController() {

        HttpHeaders headers = new HttpHeaders();
//        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        logger.info(token);
        headers.add("Authorization", token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        HttpEntity<Null> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange("http://localhost:8080/users/test", HttpMethod.GET, entity, String.class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        logger.info(result.getBody());
    }
}