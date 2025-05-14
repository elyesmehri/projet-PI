package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private int age;
    private boolean gender; // true = Male, false = Female
    private String medicalCondition;
    private String doctor;
    private String address;
    private int phoneNumber;
    private String insurance;
    private String password;
    private String medical_state;
    private String about_me;

    // Default Constructor
    public Patient() {}

    // Constructor
    public Patient(String patientName, int age, boolean gender, String medicalCondition, String doctor,
                   String address, int phoneNumber, String insurance, String password,
                   String medical_state, String about_me) {

        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.doctor = doctor;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.insurance = insurance;
        this.password = password;
        this.medical_state = medical_state;
        this.about_me = about_me;

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public boolean isGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMedical_state() {
        return medical_state;
    }

    public void setMedical_state(String medical_state) {
        this.medical_state = medical_state;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }
}
