package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;



@Setter // Add this if you're using Lombok for setters
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id") // This will create a 'doctor_id' column in the appointment table
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "family_id") // This will create a 'family_id' column in the appointment table
    private Family family;

    private LocalDateTime date;
    private Integer period;
    private Double tariff;
    private int nature;
    private String emergency;
    private int state;
    private String description;

    public Appointment(Long id,
                       Doctor doctor, Family family,
                       LocalDateTime date, Integer period,
                       Double tariff, int nature, String emergency,
                       String description, int state) {

        this.id = id;
        this.doctor = doctor;
        this.family = family;
        this.date = date;
        this.period = period;
        this.tariff = tariff;
        this.nature = nature;
        this.emergency = emergency;
        this.description = description;
        this.state = state;
    }

    public Appointment () {}

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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getTariff() {
        return tariff;
    }

    public void setTariff(Double tariff) {
        this.tariff = tariff;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
