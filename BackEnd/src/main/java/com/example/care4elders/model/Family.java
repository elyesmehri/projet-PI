package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Setter
@Entity
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyname;

    // Many-to-Many relationship with Doctor (Inverse side)
    @JsonIgnore
    @ManyToMany(mappedBy = "families") // "families" is the field name in the Doctor entity
    private List<Doctor> doctors = new ArrayList<>();

    // One-to-Many relationship with Patient (One Family has many Patients)
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients = new ArrayList<>();

    @JsonIgnore // Add this
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    private String relative;       // This might be a generic "relative" field. Consider if it's tied to a specific patient.
    private String relationship;   // This might describe the relationship of the Family head to the patients.

    private String address;
    private String phoneNumber;
    private String insurance;

    private String password; // Consider hashing passwords for security!
    private String advice;
    private float invest;
    private int login;

    public Family() {
    }

    public Family(Long id, String familyname, List<Doctor> doctors,
                  List<Patient> patients, String relative,
                  String relationship,  String address, String phoneNumber,
                  String insurance, String password,  String advice,
                  float invest, int login) {

        this.id = id;
        this.familyname = familyname;
        this.doctors = doctors;
        this.patients = patients;
        this.relative = relative;
        this.relationship = relationship;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.insurance = insurance;
        this.password = password;
        this.advice = advice;
        this.invest = invest;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public float getInvest() {
        return invest;
    }

    public void setInvest(float invest) {
        this.invest = invest;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
