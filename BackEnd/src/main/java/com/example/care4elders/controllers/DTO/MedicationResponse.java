package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationResponse {
    private Long id;
    private String medicationName;
    private String description;
    private String category;
    private String laboratory;
    private String form;
    private float price;

    // private List<String> supportedByInsuranceProviderNames;
    // private List<Long> supportedByInsuranceIds;
}
