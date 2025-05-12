package com.example.care4elders.controllers;

import com.example.care4elders.model.*;
import com.example.care4elders.services.DoctorService;
import com.example.care4elders.services.MedicationService;
import com.example.care4elders.services.PatientService;
import com.example.care4elders.services.PillularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.care4elders.services.PillularService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pillular")
public class PillularController {

    private final PillularService pillularService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MedicationService medicationService;

    @Autowired
    public PillularController(PillularService pillularService, PatientService patientService,
                              DoctorService doctorService, MedicationService medicationService) {
        this.pillularService = pillularService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.medicationService = medicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPillular(@RequestBody PillularRequest request) {
        Patient patient = patientService.getPatientByName(request.patientName);
        if (patient == null) return ResponseEntity.badRequest().body("Patient not found");

        Doctor doctor = doctorService.findByName(request.doctorname);
        if (doctor == null) return ResponseEntity.badRequest().body("Doctor not found");

        Medication medication = medicationService.findByName(request.medicationName);
        if (medication == null) return ResponseEntity.badRequest().body("Medication not found");

        Pillular pillular = new Pillular();
        pillular.setPatient(patient);
        pillular.setDoctor(doctor);
        pillular.setMedication(medication);

        // Create posology entries
        List<Posology> posologyList = new ArrayList<>();
        for (PillularRequest.PosologyRequest pr : request.posology) {
            Posology posology = new Posology();
            posology.setPillular(pillular);
            posology.setTimeSlot(pr.timeSlot);
            posology.setTaken(pr.taken);
            posologyList.add(posology);
        }
        pillular.setPosology(posologyList);

        pillularService.save(pillular);  // Save the pillular with posology

        return ResponseEntity.ok("Pillular created successfully");
    }
}
