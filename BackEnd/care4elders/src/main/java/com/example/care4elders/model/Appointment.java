package com.example.care4elders.model;

import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    private LocalDateTime date;

    @Column(nullable = false)
    private Integer period;

    @Column(nullable = false)
    private Double tariff;

    private int nature;
    private int emergency;
    private boolean quoted;
    private String description;
    private boolean skipped;

    public Appointment(Long id, Doctor doctor,
                       Family family,
                       LocalDateTime date,
                       Double tariff,
                       int nature,
                       Integer period,
                       int emergency,
                       boolean quoted,
                       String description,
                       boolean skipped) {

        this.id = id;
        this.doctor = doctor;
        this.family = family;
        this.date = date;
        this.tariff = tariff;
        this.nature = nature;
        this.period = period;
        this.emergency = emergency;
        this.quoted = quoted;
        this.description = description;
        this.skipped = skipped;
    }

    public Appointment() {}


    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Family getfamily() { return family; }
    public void setfamily(Family family) { this.family = family; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Double getTariff() { return tariff; }
    public void setTarif(Double tariff) { this.tariff = tariff; }

    public int getNature() { return nature; }
    public void setNature(int nature) { this.nature = nature; }

    public Integer getPeriod() { return period; }
    public void setPeriod(Integer period) { this.period = period; }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setTariff(Double tariff) {
        this.tariff = tariff;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    public boolean isQuoted() {
        return quoted;
    }

    public void setQuoted(boolean quoted) {
        this.quoted = quoted;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

}
