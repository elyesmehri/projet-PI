package com.example.care4elders.controllers;

import lombok.Getter;

@Getter
public class PatientPasswordUpdateRequest {

    private String patientName;
    private String password;

    public void setDoctorname(String patientName) {

        this.patientName = patientName;
    }

}

