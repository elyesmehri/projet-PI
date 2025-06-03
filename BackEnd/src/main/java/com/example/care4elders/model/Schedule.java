package com.example.care4elders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate; // The specific date for this adherence record

    @Column(name = "morning_taken", nullable = false)
    private boolean morning; // true (1): pill taken, false (0): pill not taken

    @Column(name = "noon_taken", nullable = false)
    private boolean noon; // true (1): pill taken, false (0): pill not taken

    @Column(name = "night_taken", nullable = false)
    private boolean night; // true (1): pill taken, false (0): pill not taken

    // This field will store the sum (0, 1, 2, or 3).
    // It's a derived field; we'll calculate it in the service before saving.
    @Column(name = "total_taken", nullable = false)
    private Integer total; // Sum of morning + noon + night (as 0 or 1)

    // --- Relationship to Pillular ---
    // A Schedule entry belongs to one Pillular (Prescription).
    // A Pillular can have many Schedule entries (one for each day it's tracked).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pillular_id", nullable = false) // Foreign key to Pillular
    private Pillular pillular;

    public Schedule() {

    }

    public Schedule(Long id, LocalDate scheduleDate,
                    boolean morning, boolean noon,
                    boolean night, Integer total, Pillular pillular)
    {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.morning = morning;
        this.noon = noon;
        this.night = night;
        this.total = total;
        this.pillular = pillular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isNoon() {
        return noon;
    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Pillular getPillular() {
        return pillular;
    }

    public void setPillular(Pillular pillular) {
        this.pillular = pillular;
    }
}
