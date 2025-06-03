package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSimpleResponse {
    private Long id;
    private String firstName; // Or patientName, etc.
    private String lastName;
    // Add other relevant simple fields from Patient entity
}
