package com.example.care4elders.controllers;

import lombok.Getter;

@Getter
public class DoctorPasswordUpdateRequest {

    private String doctorname;
    private String password;

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
