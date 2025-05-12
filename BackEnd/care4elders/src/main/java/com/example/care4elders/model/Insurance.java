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

    @ElementCollection
    private Map<String, Integer> supported_drugs;

    @ElementCollection
    private Map<String, Integer> supported_operations;

    public Insurance (Long id,
                      String insurance_title,
                      String PDG,
                      String medical_coding_system,
                      String description,
                      int nb_subscriber,
                      Map<String, Integer> supported_drugs,
                      Map<String, Integer> supported_operations) {

        this.id = id;
        this.insurance_title = insurance_title;
        this.supported_drugs = supported_drugs;
        this.supported_operations = supported_operations;
        this.PDG = PDG;
        this.medical_coding_system = medical_coding_system;
        this.description = description;
        this.nb_subscriber = nb_subscriber;
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
}

