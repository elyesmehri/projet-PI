package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(mappedBy = "patients") // "patients" is the field name in the Doctor entity
    private List<Doctor> doctors = new ArrayList<>(); // A patient can have many doctors

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();

    // Many-to-One relationship with Family (one Patient has one Family)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "family_id") // Foreign key in Patient table pointing to Family
    private Family family; // A patient belongs to one family

    private String patientName;
    private int age;
    private boolean gender; // true = Male, false = Female
    private String medicalCondition;

    private String address;
    private String phoneNumber;
    private String insurance;
    private String password;
    private String medical_state;
    private String about_me;

    // Default Constructor
    public Patient() {
    }

    public Patient(Long id, List<Doctor> doctors,
                   Family family, String patientName, List<Visit> visits,
                   int age, boolean gender, String medicalCondition,
                   String address, String phoneNumber, String insurance,
                   String password, String medical_state, String about_me) {

        this.id = id;
        this.doctors = doctors;
        this.family = family;
        this.visits = visits;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.insurance = insurance;
        this.password = password;
        this.medical_state = medical_state;
        this.about_me = about_me;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMedical_state(String medical_state) {
        this.medical_state = medical_state;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }


    public void addVisit(Visit visit) {
        if (this.visits == null) {
            this.visits = new ArrayList<>();
        }
        if (!this.visits.contains(visit)) {
            this.visits.add(visit);
            visit.setPatient(this);
        }
    }
    public void removeVisit(Visit visit) {
        if (this.visits != null && this.visits.contains(visit)) {
            this.visits.remove(visit);
            visit.setPatient(null); // Or detach
        }
    }
}
