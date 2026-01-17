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
}

