package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PillularRequest {


    private LocalDateTime prescriptionDate;
    private String dosage;
    private String frequency;
    private String duration;
    private Integer quantity; // Can be null if not applicable (e.g., for liquids)
    private String instructions;

    // IDs for relationships
    private Long doctorId;
    private Long patientId;
    private Long medicationId;
    private Long insuranceId; // Optional, can be null

    // Fields for insurance coverage (can be set by client or calculated in service)
    private Boolean isCoveredByInsurance;
    private Float coveredAmount;
    private Float patientOutOfPocket;
}
