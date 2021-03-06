package org.simplepay.paysystem.login.service;

import org.simplepay.paysystem.login.model.ApplicationRole;
import org.simplepay.paysystem.login.repository.ApplicationRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationRoleService {

    Logger logger = LoggerFactory.getLogger(ApplicationRoleService.class);

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";


    @Autowired
    ApplicationRoleRepository applicationRoleRepository;


    public ApplicationRole getRole(String authority) {
        logger.info("Get role");
        ApplicationRole role = applicationRoleRepository.findByAuthority(authority);
        if (role == null) {
            role = new ApplicationRole();
            role.setAuthority(authority);
            applicationRoleRepository.save(role);
        }
        return role;
    }
}
