package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientRequest {

    private String patientName;
    private int age;
    private boolean gender;
    private String medicalCondition;
    private String doctorname;
    private String address;
    private String phoneNumber;
    private String insurance;
    private String password;
    private String medical_state;
    private String about_me;

    public boolean getGender() {
        return gender;
    }

    public boolean isGender() {
        return gender;
    }
}
