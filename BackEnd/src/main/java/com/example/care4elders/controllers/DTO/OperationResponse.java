package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationResponse {
    private Long id;
    private String operationName;
    private String operationCode;
    private String description;
    private Float standardPrice;
    private String category;
    // You could add a count of supporting insurances if desired
    // private int numberOfSupportingInsurances;
}
