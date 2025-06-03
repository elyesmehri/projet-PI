package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyRequest {

    private Long id;

    private String familyname;
    private String patientName;
    private String doctorname;

    private String relative;
    private String relationship;

    private String address;
    private String phoneNumber;
    private String insurance;
    private float  invest;

    private String password;
    private String advice;
    private int login;

}


