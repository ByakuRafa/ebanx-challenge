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
        System.out.print(accountID);
        System.out.print("---->[Get Balance}");

        Double balance = service.getBalance(accountID);
        System.out.println(balance);

        if (balance == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/event")
    public ResponseEntity<Object> handleEvent(@RequestBody EventRequest request){
        //deposit
        if ("deposit".equals(request.getType())){
            System.out.print("request entred depoist IF\n");
            var account = service.deposit(request.getDestination(), request.getAmount());
            System.out.print("Service invoqued\n");
            
            return new ResponseEntity<>(Map.of("destination", account),HttpStatus.CREATED);
            
        }

        //withdraw
        else if("withdraw".equals(request.getType())){
            var account = service.withdraw(request.getOrigin(), request.getAmount());
            
            if (account == null) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(Map.of("origin", account), HttpStatus.CREATED);
        }

        else if("transfer".equals(request.getType())){
            var result = service.transfer(request.getOrigin(), request.getDestination(), request.getAmount());
            if (result == null){
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(Map.of("origin", result.origin(), "destination", result.destination()), HttpStatus.CREATED);
        }


        //out of scope requests
        return ResponseEntity.badRequest().build();
        
    }
 
}
