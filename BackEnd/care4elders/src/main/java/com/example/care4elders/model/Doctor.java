package com.example.care4elders.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorname;
    private String medicalID;
    private String speciality;
    private String address;
    private int score;
    private String phonenumber;
    private String insurance;
    private String hospital;
    private int numberofpatients;
    private String password;
    private Boolean gender;

    // Constructors
    public Doctor() {}

    public Doctor(Long id,
                  String doctorname,
                  String medicalID,
                  String speciality,
                  String address,
                  int score,
                  String phonenumber,
                  String insurance,
                  String hospital,
                  int numberofpatients,
                  String password,
                  boolean gender) {

        this.id = id;
        this.doctorname = doctorname;
        this.medicalID = medicalID;
        this.speciality = speciality;
        this.address = address;
        this.score = score;
        this.phonenumber = phonenumber;
        this.insurance = insurance;
        this.hospital = hospital;
        this.numberofpatients = numberofpatients;
        this.password = password;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getNumberofpatients() {
        return numberofpatients;
    }

    public void setNumberofpatients(int numberofpatients) {
        this.numberofpatients = numberofpatients;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(String medicalID) {
        this.medicalID = medicalID;
    }
}

