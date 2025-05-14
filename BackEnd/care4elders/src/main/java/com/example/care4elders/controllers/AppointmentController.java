package com.example.care4elders.controllers;

import com.example.care4elders.model.*;
import com.example.care4elders.services.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.care4elders.controllers.AppointmentRequest;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final FamilyService familyService;
    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody AppointmentRequest request) {
        Doctor doctor = doctorService.findByName(request.doctorname);
        if (doctor == null) return ResponseEntity.badRequest().body("Doctor not found");

        Family family = familyService.getFamilyByName(request.familyname);
        if (family == null) return ResponseEntity.badRequest().body("Family not found");


        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setfamily(family);
        appointment.setDate(request.date);
        appointment.setTarif(request.tariff);
        appointment.setNature(request.nature);
        appointment.setPeriod(request.period);
        appointment.setEmergency(request.emergency);
        appointment.setQuoted(request.quoted);
        appointment.setDescription(request.description);
        appointment.setSkipped(request.skipped);


        appointmentService.save(appointment);

        return ResponseEntity.ok("Rendez-vous enregistré avec succès");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        boolean deleted = appointmentService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Appointment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody Appointment updated) {
        boolean result = appointmentService.updateAppointment(id, updated);
        if (result) {
            return ResponseEntity.ok("Appointment updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllAppointments() {
        try {
            appointmentService.deleteAllAppointments();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
