package com.example.care4elders.model;

import jakarta.persistence.*;
import lombok.Setter;


@Setter
@Entity
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String familyname;
    private String patientname;
    private String relative;
    private String relationship;
    private String address;
    private String phonenumber;
    private String insurance;
    private String doctors ;
    private String password;
    private String advice;
    private float  invest;
    private int login;


    public Family(Long id,
                  String familyname,
                  String patientname,
                  String relative,
                  String relationship,
                  String address,
                  String phonenumber,
                  String insurance,
                  String doctors,
                  String password,
                  String advice,
                  float invest,
                  int login ) {
        this.id = id;
        this.familyname = familyname;
        this.patientname = patientname;
        this.relative = relative;
        this.relationship = relationship;
        this.address = address;
        this.phonenumber = phonenumber;
        this.insurance = insurance;
        this.doctors = doctors;
        this.password = password;
        this.advice = advice;
        this.invest = invest;
        this.login =  login;
    }

    public Family() {

    }

    public String getRelatioship() {
        return relationship;
    }

    public void setRelatioship(String relationship) {
        this.relationship = relationship;
    }

    public float getInvest() {
        return invest;
    }

    public void setInvest(float invest) {
        this.invest = invest;
    }

    public String getDoctors() {
        return doctors;
    }

    public void setDoctors(String doctors) {
        this.doctors = doctors;
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

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public float getInvsest() {
        return invest;
    }

    public void setInvsest(float invest) {
        this.invest = invest;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }
}
