package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OperationRequest {
    private String operationName;
    private String operationCode;
    private String description;
    private Float standardPrice;
    private String category;
}
