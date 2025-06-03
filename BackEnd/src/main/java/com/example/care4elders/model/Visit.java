package com.example.care4elders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "visit") // Recommended to use singular for entity table names
@Getter
@Setter
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    // Using a single boolean 'isAssisted' is more common than 'assisted' and 'skipped'.
    // If 'isAssisted' is true, it was assisted. If false, it implies skipped/not assisted.
    // For more complex states (e.g., 'rescheduled', 'canceled'), an Enum would be better.
    @Column(name = "is_assisted", nullable = false)
    private boolean isAssisted;

    // Many-to-One relationship with Doctor
    // A Visit belongs to one Doctor.
    // 'doctor_id' column in the 'visit' table will be the foreign key.
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading is generally preferred for ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false) // Ensures every visit must have a doctor
    private Doctor doctor;

    // Many-to-One relationship with Patient
    // A Visit belongs to one Patient.
    // 'patient_id' column in the 'visit' table will be the foreign key.
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading is generally preferred for ManyToOne
    @JoinColumn(name = "patient_id", nullable = false) // Ensures every visit must have a patient
    private Patient patient;

    public Visit() {}

    public Visit(LocalDateTime visitDate, boolean isAssisted, Doctor doctor, Patient patient) {
        this.visitDate = visitDate;
        this.isAssisted = isAssisted;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public boolean isAssisted() {
        return isAssisted;
    }

    public void setAssisted(boolean assisted) {
        isAssisted = assisted;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
