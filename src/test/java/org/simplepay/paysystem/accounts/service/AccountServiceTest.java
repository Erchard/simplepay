package org.simplepay.paysystem.accounts.service;

import org.junit.jupiter.api.Test;
import org.simplepay.paysystem.accounts.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountService accountService;

    final String ExternalPaySystemA = "00000A";
    final String ExternalPaySystemB = "00000B";
    final String AccountA = "000001";
    final String AccountB = "000002";
    final String AccountC = "000003";
    final String AccountD = "000004";


    @Test
    void createNewAccount() {

        Account account = accountService.createNewAccount(AccountA);
        logger.info("Account Id: {}", account.getId());
        assertEquals(account.getNumber(), AccountA);
    }


    @Test
    void changeCreditLimit() {
        accountService.createNewAccount(AccountC);
        accountService.changeCreditLimit(AccountC, BigDecimal.TEN);
        Account account = accountService.getByNumber(AccountC);
        assertEquals(account.getCreditLimit().compareTo(BigDecimal.TEN), 0);
    }

    @Test
    void transfer() {
        accountService.createNewAccount(AccountB);
        accountService.createNewAccount(AccountD);
        accountService.changeCreditLimit(AccountB, BigDecimal.TEN);
        accountService.transfer(AccountB, AccountD, new BigDecimal("3.45"));
        Account accountB = accountService.getByNumber(AccountB);
        Account accountD = accountService.getByNumber(AccountD);
        logger.info("B balance: {}", accountB.getBalance());
        logger.info("D balance: {}", accountD.getBalance());
        assertEquals(accountB.getBalance().negate(), accountD.getBalance());
    }
}