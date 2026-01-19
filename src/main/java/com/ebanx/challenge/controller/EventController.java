package com.ebanx.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ebanx.challenge.dto.EventRequest;
import com.ebanx.challenge.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

@RestController
public class EventController {

    private final AccountService service;

    public EventController(AccountService service){
        this.service = service;
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam("account_id") String accountID){
        Double balance = service.getBalance(accountID);
        if (balance == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/event")
    public ResponseEntity<Object> handleEvent(@RequestBody EventRequest request){
        
        //deposit
        if ("deposit".equals(request.getType())){
            var account = service.deposit(request.getDestination(), request.getAmount());
            return new ResponseEntity<>(Map.of("destination", account),HttpStatus.CREATED);
            
        }

        if("withdraw".equals(request.getType())){
            var account = service.withdraw(request.getOrigin(), request.getAmount());
            
            if (account == null) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(Map.of("origin", account), HttpStatus.CREATED);
        }

        //out of scope requests
        return ResponseEntity.badRequest().build();
        
    }
 
}
