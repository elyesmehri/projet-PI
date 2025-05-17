package com.example.care4elders.controllers;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class FamilyRequest {

    private Long id;

    private String familyname;
    private Patient patientName;
    private Doctor doctorname;

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


