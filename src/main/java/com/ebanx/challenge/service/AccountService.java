package com.ebanx.challenge.service;

import com.ebanx.challenge.model.Account;
import com.ebanx.challenge.repository.AccountRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public Double getBalance(String accountId){
        Optional<Account> account = repository.findById(accountId);
        return account.map(Account::getBalance).orElse(null);
    }

       public Account deposit(String destinationid, Double amount){
        Account account = repository.findById(destinationid).orElse(new Account(destinationid, 0.0));
        account.setBalance(account.getBalance() + amount);
        return repository.save(account);
    }

}

