package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorResponse {

    private Long id;
    private String doctorname;
    private String medicalID;
    private String speciality;
    private String address;
    private int score;
    private String phonenumber;
    private String hospital;
    private int numberofpatients;
    private String password; // Remember to hash this on the server!
    private Boolean gender;

    // To link to EXISTING families and patients when creating/updating a Doctor:
    private List<Long> familyIds;   // List of IDs of families this doctor should be associated with
    private List<Long> patientIds;  // List of IDs of patients this doctor should be associated with

    private List<InsuranceSimpleResponse> affiliatedInsurances;

}
