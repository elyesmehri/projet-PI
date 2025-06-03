package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequest {

    private String patientName;
    private Boolean gender; // Assuming gender is boolean (true for Male, false for Female, null for unknown)
    private Integer age;
    private String address;
    private String phoneNumber;
    private String medical_state; // Use the exact field name from your Patient entity
    private String about_me;
}
