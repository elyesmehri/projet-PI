package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsuranceResponse {
    private Long id;
    private String providerName;
    private String policyNumber;
    private String coverageDetails;
    // Optional: Could include counts of related entities for quick overview
    private int numberOfLinkedPrescriptions;

    // New: Lists of supported medications and operations (simplified DTOs or just names/IDs)
    private List<MedicationResponse> supportedMedications; // Or just List<String> medicationNames
    private List<OperationResponse> supportedOperations; // Or just List<String> operationNames
}
