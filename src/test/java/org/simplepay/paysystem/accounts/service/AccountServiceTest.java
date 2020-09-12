package org.simplepay.paysystem.accounts.service;

import org.junit.jupiter.api.Test;
import org.simplepay.paysystem.accounts.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountService accountService;

    final String currencyId = "USD";

    final String ExternalPaySystemA = "00000A";
    final String ExternalPaySystemB = "00000B";
    final String AccountA = "000001";
    final String AccountB = "000002";
    final String AccountC = "000003";
    final String AccountD = "000004";
    final String AccountE = "000005";
    final String AccountF = "000006";
    final String AccountG = "000007";
    final String AccountH = "000008";
    final String AccountI = "000009";
    final String AccountJ = "000010";
    final String AccountK = "000011";
    final String AccountL = "000012";


    @Test
    void createNewAccount() {

        Account account = accountService.createNewAccount(currencyId, AccountA);
        logger.info("Account Id: {}", account.getId());
        assertEquals(account.getNumber(), AccountA);
    }


    @Test
    void changeCreditLimit() {
        accountService.createNewAccount(currencyId, AccountC);
        accountService.changeCreditLimit(AccountC, BigDecimal.TEN);
        Account account = accountService.getByNumber(AccountC);
        assertEquals(account.getCreditLimit().compareTo(BigDecimal.TEN), 0);
    }

    @Test
    void testTransfer() {
        Map<String, BigDecimal> inputs = new HashMap<>();
        Map<String, BigDecimal> outputs = new HashMap<>();

        accountService.createNewAccount(currencyId, AccountB);
        accountService.changeCreditLimit(AccountB, BigDecimal.TEN);
        inputs.put(AccountB, new BigDecimal("3.15"));

        accountService.createNewAccount(currencyId, AccountD);
        accountService.changeCreditLimit(AccountD, BigDecimal.TEN);
        inputs.put(AccountD, new BigDecimal("7.20"));


        accountService.createNewAccount(currencyId, AccountE);
        outputs.put(AccountE,new BigDecimal("1.00"));

        accountService.createNewAccount(currencyId, AccountF);
        outputs.put(AccountF,new BigDecimal("5.00"));

        accountService.createNewAccount(currencyId, AccountG);
        outputs.put(AccountG,new BigDecimal("4.35"));

        accountService.transfer(inputs,outputs);

        Account account = accountService.getByNumber(AccountF);

        assertEquals(0,new BigDecimal("5.00").compareTo(account.getBalance()));
    }
}