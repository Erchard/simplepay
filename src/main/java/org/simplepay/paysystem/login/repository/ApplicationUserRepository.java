package org.simplepay.paysystem.login.repository;

import org.simplepay.paysystem.login.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {

    ApplicationUser findByUsername(String username);
}
