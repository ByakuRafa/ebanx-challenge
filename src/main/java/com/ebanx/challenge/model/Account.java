package com.ebanx.challenge.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private String id;
    private Double balance;
}
