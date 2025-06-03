package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsuranceRequest {
    private String providerName;
    private String policyNumber;
    private String coverageDetails;
    // New: List of IDs for supported medications and operations
    private List<Long> supportedMedicationIds;
    private List<Long> supportedOperationIds;


 }


