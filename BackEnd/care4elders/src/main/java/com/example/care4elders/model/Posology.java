package com.example.care4elders.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Posology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pillular_id")
    private Pillular pillular;

    private String timeSlot; // "matin", "midi", or "soir"
    private Boolean taken;   // true or false for whether the pill was taken

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pillular getPillular() { return pillular; }
    public void setPillular(Pillular pillular) { this.pillular = pillular; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public Boolean getTaken() { return taken; }
    public void setTaken(Boolean taken) { this.taken = taken; }
}
