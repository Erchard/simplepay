package org.simplepay.paysystem.login.service;

import com.google.gson.Gson;
import org.simplepay.paysystem.login.model.ApplicationUser;
import org.simplepay.paysystem.login.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {

    Logger logger = LoggerFactory.getLogger(ApplicationUserService.class);
    @Autowired
    Gson gson;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public void signUp(ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
        logger.info(gson.toJson(applicationUser));
    }
}
