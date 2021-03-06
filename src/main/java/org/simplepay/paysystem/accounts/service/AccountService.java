package org.simplepay.paysystem.accounts.service;

import org.simplepay.paysystem.accounts.model.Account;
import org.simplepay.paysystem.accounts.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    Account createNewAccount(String currencyId, String number) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCurrencyId(currencyId);
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
    boolean simpleTransfer(String numberFrom, String numberTo, BigDecimal amount) {
        Account accountFrom = getByNumber(numberFrom);
        Account accountTo = getByNumber(numberTo);
        if(!accountFrom.getCurrencyId().equals(accountTo.getCurrencyId())){
            logger.error("Currency of accounts is not equal");
            return false;
        }

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

    @Transactional
    public boolean transfer(Map<String, BigDecimal> inputList, Map<String, BigDecimal> outputList) {
        BigDecimal inputTotal = BigDecimal.ZERO;
        BigDecimal outputTotal = BigDecimal.ZERO;
        List<Account> accountList = new ArrayList<>();
        Account account;
        BigDecimal balance;
        BigDecimal amount;
        String currencyId = null;
        for (Map.Entry<String, BigDecimal> input : inputList.entrySet()) {
            amount = input.getValue();
            account = getByNumber(input.getKey());
            if(currencyId==null){
                currencyId = account.getCurrencyId();
            }
            if(!currencyId.equals(account.getCurrencyId())){
                logger.error("Currency of accounts is not equal");
                return false;
            }
            balance = account.getBalance();
            if (balance.add(account.getCreditLimit()).compareTo(amount) < 0) {
                logger.error("The transfer amount is too large. Not enough money to transfer between accounts.");
                return false;
            }
            account.setBalance(balance.add(amount));
            accountList.add(account);
            inputTotal = inputTotal.add(amount);
        }
        for (Map.Entry<String, BigDecimal> output : outputList.entrySet()) {
            account = getByNumber(output.getKey());
            account.setBalance(account.getBalance().add(output.getValue()));
            outputTotal = outputTotal.add(output.getValue());
        }
        if (outputTotal.compareTo(inputTotal) != 0) {
            throw new IllegalArgumentException("Inputs total not equal outputs total");
        }
        accountRepository.saveAll(accountList);
        return true;
    }



}
