package com.ebanx.challenge.dto;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import lombok.Data;


@Data
public class EventRequest {
    private String type;
    private Double amount;
    private String origin;
    private String destination;
}
