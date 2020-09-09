package org.simplepay.paysystem.login.repository;

import org.simplepay.paysystem.login.model.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole,Long> {
    ApplicationRole findByAuthority(String authority);
}
