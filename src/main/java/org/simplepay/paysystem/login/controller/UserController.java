package org.simplepay.paysystem.login.controller;

import com.google.gson.Gson;
import org.simplepay.paysystem.login.model.ApplicationUser;
import org.simplepay.paysystem.login.service.ApplicationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ApplicationUserService applicationUserService;

    @Autowired
    Gson gson;

    @PostMapping("/sign-up")
    void signUp(@RequestBody ApplicationUser applicationUser) {
        logger.info(gson.toJson(applicationUser));
        applicationUserService.signUp(applicationUser);
    }

}
