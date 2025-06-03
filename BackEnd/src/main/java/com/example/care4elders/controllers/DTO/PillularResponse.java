package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PillularResponse {

    private Long id;
    private LocalDateTime prescriptionDate;
    private String dosage;
    private String frequency;
    private String duration;
    private Integer quantity;
    private String instructions;

    // Details from related entities for convenience
    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;

    private Long medicationId;
    private String medicationName; // Name of the prescribed medication
    private String medicationCategory; // Category of the prescribed medication

    private Long insuranceId;
    private String insuranceProviderName;
    private String insurancePolicyNumber;

    // Insurance coverage details
    private Boolean isCoveredByInsurance;
    private Float coveredAmount;
    private Float patientOutOfPocket;
}
