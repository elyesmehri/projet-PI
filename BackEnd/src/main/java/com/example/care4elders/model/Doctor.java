package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Cascade types
    @JoinTable(
            name = "doctor_s_family_s", // Name of the join table
            joinColumns = @JoinColumn(name = "doctor_id"), // Column in join table referring to Doctor
            inverseJoinColumns = @JoinColumn(name = "family_id") // Column in join table referring to Family
    )
    private List<Family> families = new ArrayList<>(); // Must be a collection!

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Cascade types
    @JoinTable(
            name = "doctor_s_patient_s", // Name of the join table
            joinColumns = @JoinColumn(name = "doctor_id"), // Column in join table referring to Doctor
            inverseJoinColumns = @JoinColumn(name = "patient_id") // Column in join table referring to Family
    )
    private List<Patient> patients = new ArrayList<>(); // Must be a collection!


    @JsonIgnore // IMPORTANT: Prevents infinite recursion when serializing Doctor to JSON
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointment = new ArrayList<>(); // Initialize to prevent NullPointerException

    @JsonIgnore // IMPORTANT: Prevent infinite recursion
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>(); // Initialize the lis

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_insurance_affiliation", // Name of the join table
            joinColumns = @JoinColumn(name = "doctor_id"), // Column in join table for Doctor
            inverseJoinColumns = @JoinColumn(name = "insurance_id") // Column in join table for Insurance
    )
    private List<Insurance> affiliatedInsurances = new ArrayList<>();

    private String doctorname;
    private String medicalID;
    private String speciality;
    private String address;
    private int score;
    private String phonenumber;
    private String hospital;
    private int numberofpatients;
    private String password;
    private Boolean gender;

    public Doctor() {
    }


    public Doctor(Long id,
                  List<Family> families, List<Patient> patients, List<Appointment> appointment,
                  List<Visit> visits, String doctorname, String medicalID,
                  String speciality, String address, int score, String phonenumber,
                  String hospital, int numberofpatients, String password, Boolean gender) {

        this.id = id;
        this.families = families;
        this.patients = patients;
        this.appointment = appointment;
        this.visits = visits;
        this.doctorname = doctorname;
        this.medicalID = medicalID;
        this.speciality = speciality;
        this.address = address;
        this.score = score;
        this.phonenumber = phonenumber;
        this.hospital = hospital;
        this.numberofpatients = numberofpatients;
        this.password = password;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(String medicalID) {
        this.medicalID = medicalID;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getNumberofpatients() {
        return numberofpatients;
    }

    public void setNumberofpatients(int numberofpatients) {
        this.numberofpatients = numberofpatients;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    // Inside your Doctor.java entity class:

    public void addFamily(Family family) {
        this.families.add(family); // Add the family to the doctor's collection
        if (family != null && !family.getDoctors().contains(this)) {
            family.getDoctors().add(this); // Also add the doctor to the family's collection (for bidirectionality)
        }
    }

    public void removeFamily(Family family) {
        this.families.remove(family);
        if (family != null) {
            family.getDoctors().remove(this);
        }
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient); // Add the patient to the doctor's collection
        if (patient != null && !patient.getDoctors().contains(this)) {
            patient.getDoctors().add(this); // Also add the doctor to the patient's collection (for bidirectionality)
        }
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
        if (patient != null) {
            patient.getDoctors().remove(this);
        }
    }

    public void addAppointment(Appointment appointment) {
        if (this.appointment == null) {
            this.appointment = new ArrayList<>();
        }
        if (!this.appointment.contains(appointment)) {
            this.appointment.add(appointment);
            appointment.setDoctor(this); // Set the doctor on the appointment
        }
    }

    public void removeAppointment(Appointment appointment) {
        if (this.appointment != null && this.appointment.contains(appointment)) {
            this.appointment.remove(appointment);
            appointment.setDoctor(null); // Remove the doctor from the appointment
        }
    }


    public void addVisit(Visit visit) {
        if (this.visits == null) {
            this.visits = new ArrayList<>();
        }
        if (!this.visits.contains(visit)) {
            this.visits.add(visit);
            visit.setDoctor(this);
        }
    }

    public void removeVisit(Visit visit) {
        if (this.visits != null && this.visits.contains(visit)) {
            this.visits.remove(visit);
            visit.setDoctor(null); // Or detach, depending on desired behavior
        }
    }

}

