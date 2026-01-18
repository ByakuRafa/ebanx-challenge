package com.ebanx.challenge.repository;
import com.ebanx.challenge.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {
    private final Map<String, Account> database = new ConcurrentHashMap<>();

    public Optional<Account> findById(String id){
        Account account = database.get(id);
        if (account == null){
             return Optional.empty();
            }
        return Optional.of(null);
    }

    public Account save(Account acciut){
        return acciut;
    }

    public void clear(){
        database.clear();
    }

    public boolean exists(String id){
        return database.containsKey(id);
    }
   
}
