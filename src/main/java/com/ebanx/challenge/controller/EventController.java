package com.ebanx.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ebanx.challenge.service.AccountService;

import org.springframework.http.HttpStatus;

@RestController
public class EventController {

    private final AccountService service;

    public EventController(AccountService service){
        this.service = service;
    }

    public ResponseEntity<Double> getBalance(@RequestParam("account_id") String accountID){
        Double balance = service.getBalance(accountID);
        return ResponseEntity.ok(balance);
    }

 
}
