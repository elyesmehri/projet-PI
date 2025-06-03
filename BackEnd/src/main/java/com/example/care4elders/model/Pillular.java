package com.example.care4elders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pillular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One with Doctor (The Prescriber)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Many-to-One with Patient (The Recipient)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many-to-One with Medication (The type of drug from the catalog)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "prescription_date", nullable = false)
    private LocalDateTime prescriptionDate; // This was missing in your constructor

    @Column(name = "dosage", nullable = false)
    private String dosage; // This was missing

    @Column(name = "frequency")
    private String frequency; // This was missing

    @Column(name = "duration")
    private String duration; // This was missing

    @Column(name = "quantity")
    private Integer quantity; // This was missing

    @Column(name = "instructions", length = 500)
    private String instructions; // This was missing

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id") // Nullable if the prescription is not linked to an insurance or is self-paid
    private Insurance insurance;

    // --- Fields for Insurance Coverage/Discount Calculation (business logic) ---
    @Column(name = "is_covered_by_insurance")
    private Boolean isCoveredByInsurance; // Whether this specific prescription is covered

    @Column(name = "covered_amount")
    private Float coveredAmount; // How much the insurance covered (e.g., 80% of price)

    @Column(name = "patient_out_of_pocket")
    private Float patientOutOfPocket; // The amount the patient has to pay after insurance

    @OneToMany(mappedBy = "pillular", cascade = CascadeType.ALL)
    private List<Schedule> Schedule;

    public Pillular() {}

    public Pillular(Long id, Doctor doctor, Patient patient, Medication medication,
                    LocalDateTime prescriptionDate, String dosage, String frequency,
                    String duration, Integer quantity, String instructions, Insurance insurance,
                    Boolean isCoveredByInsurance, Float coveredAmount, Float patientOutOfPocket,
                    List<Schedule> schedule) {

        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.medication = medication;
        this.prescriptionDate = prescriptionDate;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.quantity = quantity;
        this.instructions = instructions;
        this.insurance = insurance;
        this.isCoveredByInsurance = isCoveredByInsurance;
        this.coveredAmount = coveredAmount;
        this.patientOutOfPocket = patientOutOfPocket;
        Schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Boolean getCoveredByInsurance() {
        return isCoveredByInsurance;
    }

    public void setCoveredByInsurance(Boolean coveredByInsurance) {
        isCoveredByInsurance = coveredByInsurance;
    }

    public Float getCoveredAmount() {
        return coveredAmount;
    }

    public void setCoveredAmount(Float coveredAmount) {
        this.coveredAmount = coveredAmount;
    }

    public Float getPatientOutOfPocket() {
        return patientOutOfPocket;
    }

    public void setPatientOutOfPocket(Float patientOutOfPocket) {
        this.patientOutOfPocket = patientOutOfPocket;
    }

    public List<Schedule> getSchedule() {
        return Schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        Schedule = schedule;
    }
}
