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
        return account.map(Account::getBalance).orElse(0.0);
    }

       public Account deposit(String destinationid, Double amount){
        Account account = repository.findById(destinationid).orElse(null);
        account.setBalance(account.getBalance() + amount);
        return repository.save(account);
    }

}

