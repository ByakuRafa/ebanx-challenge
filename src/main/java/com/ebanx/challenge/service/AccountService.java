package com.ebanx.challenge.service;

import com.ebanx.challenge.model.Account;
import com.ebanx.challenge.repository.AccountRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.UrlHandlerFilter.Builder.TrailingSlashSpec;

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

    public Account withdraw(String destinationid, Double amount){
        Optional<Account> accountOpt = repository.findById(destinationid);
        
        if (accountOpt.isEmpty()) {
            return null;
        }

        Account account = accountOpt.get();
        account.setBalance(account.getBalance() - amount);
        return repository.save(account);        
    }

    public TransferResult transfer(String originId, String destinationId, Double amount){
      
        Account origin = withdraw(originId, amount);
        if (origin == null){
            return null;
        }
        Account destination =  deposit(destinationId, amount);

        return new TransferResult(origin, destination);
    }

    public void reset(){
        repository.clear();
    }


    public record TransferResult(Account origin, Account destination) {
    }

}

