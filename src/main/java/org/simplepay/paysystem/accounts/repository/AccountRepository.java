package org.simplepay.paysystem.accounts.repository;

import org.simplepay.paysystem.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByNumber(String number);
}
