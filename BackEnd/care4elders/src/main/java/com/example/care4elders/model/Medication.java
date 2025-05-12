package com.example.care4elders.model;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Medication {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicationName;
    private String dosage;
    private String description;
    private String category;
    private String laboratory; // Nouveau champ pour le laboratoire
    private String form; // Nouveau champ pour la forme (comprimé, gélule, etc.)

    // Constructors
    public Medication() {}

    public Medication(String medicationName,
                      String dosage,
                      String description,
                      String category,
                      String laboratory,
                      String form) {

        this.medicationName = medicationName;
        this.dosage = dosage;
        this.description = description;
        this.category = category;
        this.laboratory = laboratory;
        this.form = form;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void medicationname(String medicationname) {
        this.medicationName = medicationName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Long getId() {
        return id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public String getForm() {
        return form;
    }
}

