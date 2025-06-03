package com.example.care4elders.controllers.DTO;

import lombok.*;


@Getter
@Setter
public class CheckDoctorRequest {

    private Long id;
    private String medicalID;
    private String password;

    // Default constructor (required for Jackson)
    public CheckDoctorRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(String medicalID) {
        this.medicalID = medicalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

