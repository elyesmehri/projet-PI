package com.example.care4elders.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Map;

@Setter
@Entity
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String insurance_title;
    private String PDG;
    private String medical_coding_system ;
    private String description;
    private int nb_subscriber;
    private String contact_phone;

    @ElementCollection
    private Map<String, Integer> supported_drugs;

    @ElementCollection
    private Map<String, Integer> supported_operations;

    @ElementCollection
    @CollectionTable(name = "insurance_available_doctors", joinColumns = @JoinColumn(name = "insurance_id"))
    @MapKeyColumn(name = "doctor_speciality") // Use a single string as the key for the composite value
    @Column(name = "doctor_id")
    private Map<String, Integer> available_doctors;

    public Insurance (Long id,
                      String insurance_title,
                      String PDG,
                      String medical_coding_system,
                      String description,
                      int nb_subscriber,
                      Map<String, Integer> supported_drugs,
                      Map<String, Integer> supported_operations,
                      Map<String, Integer> available_doctors
                      ) {

        this.id = id;
        this.insurance_title = insurance_title;
        this.supported_drugs = supported_drugs;
        this.supported_operations = supported_operations;
        this.PDG = PDG;
        this.medical_coding_system = medical_coding_system;
        this.description = description;
        this.nb_subscriber = nb_subscriber;
        this.available_doctors = available_doctors;
        this.contact_phone = contact_phone;
    }

    public Insurance() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsurance_title() {
        return insurance_title;
    }

    public void setInsurance_title(String insurance_title) {
        this.insurance_title = insurance_title;
    }

    public String getPDG() {
        return PDG;
    }

    public void setPDG(String PDG) {
        this.PDG = PDG;
    }

    public String getMedical_coding_system() {
        return medical_coding_system;
    }

    public void setMedical_coding_system(String medical_coding_system) {
        this.medical_coding_system = medical_coding_system;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNb_subscriber() {
        return nb_subscriber;
    }

    public void setNb_subscriber(int nb_subscriber) {
        this.nb_subscriber = nb_subscriber;
    }

    public Map<String, Integer> getSupported_drugs() {
        return supported_drugs;
    }

    public void setSupported_drugs(Map<String, Integer> supported_drugs) {
        this.supported_drugs = supported_drugs;
    }

    public Map<String, Integer> getSupported_operations() {
        return supported_operations;
    }

    public void setSupported_operations(Map<String, Integer> supported_operations) {
        this.supported_operations = supported_operations;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public Map<String, Integer> getAvailable_doctors() {
        return available_doctors;
    }

    public void setAvailable_doctors(Map<String, Integer> available_doctors) {
        this.available_doctors = available_doctors;
    }
}

