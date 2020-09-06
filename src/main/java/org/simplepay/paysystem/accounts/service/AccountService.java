package org.simplepay.paysystem.accounts.service;

import org.simplepay.paysystem.accounts.model.Account;
import org.simplepay.paysystem.accounts.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    Account createNewAccount(String number) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setNumber(number);
        account.setCreditLimit(BigDecimal.ZERO);
        accountRepository.save(account);
        return account;
    }

    Account changeCreditLimit(String number, BigDecimal newLimit) {
        Account account = getByNumber(number);
        account.setCreditLimit(newLimit);
        accountRepository.save(account);
        return account;
    }

    Account getByNumber(String number) {
        Account account = accountRepository.findByNumber(number);
        if (account == null) {
            throw new IllegalArgumentException("Account number not found");
        }
        return account;
    }

    @Transactional
    boolean transfer(String numberFrom, String numberTo, BigDecimal amount) {
        Account accountFrom = getByNumber(numberFrom);
        Account accountTo = getByNumber(numberTo);
        if (accountFrom.getBalance().add(accountFrom.getCreditLimit()).compareTo(amount) < 0) {
            logger.error("The transfer amount is too large. Not enough money to transfer between accounts.");
            return false;
        }
        accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
        accountTo.setBalance(accountTo.getBalance().add(amount));
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return true;
    }

//TODO: transfer with multiple inputs and outputs

}
