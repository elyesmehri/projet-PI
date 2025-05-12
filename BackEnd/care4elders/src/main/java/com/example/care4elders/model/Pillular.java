package com.example.care4elders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Pillular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;


    @OneToMany(mappedBy = "pillular", cascade = CascadeType.ALL)
    private List<Posology> posology;  // A list of Posology entries (morning, afternoon, evening)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public List<Posology> getPosology() {
        return posology;
    }

    public void setPosology(List<Posology> posology) {
        this.posology = posology;
    }
}
