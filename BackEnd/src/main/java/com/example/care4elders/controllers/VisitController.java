package com.example.care4elders.controllers;


import com.example.care4elders.controllers.DTO.VisitRequest;
import com.example.care4elders.controllers.DTO.VisitResponse;

import com.example.care4elders.services.*;

import com.example.care4elders.services.AppointmentService;
import com.example.care4elders.services.DoctorService;
import com.example.care4elders.services.FamilyService;
import com.example.care4elders.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final FamilyService familyService;
    private final AppointmentService appointmentService;
    private final VisitService visitService;


    // CREATE Visit
    @PostMapping("/book")
    public ResponseEntity<VisitResponse> createVisit(@RequestBody VisitRequest request) {
        try {
            VisitResponse newVisit = visitService.createVisit(request);
            return new ResponseEntity<>(newVisit, HttpStatus.CREATED); // 201 Created
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request if Doctor/Patient not found
        }
    }

    // READ All Visits
    @GetMapping("/all")
    public ResponseEntity<List<VisitResponse>> getAllVisits() {
        List<VisitResponse> visits = visitService.getAllVisits();
        return ResponseEntity.ok(visits); // 200 OK
    }

    // READ Visit by ID
    @GetMapping("/{id}")
    public ResponseEntity<VisitResponse> getVisitById(@PathVariable Long id) {
        try {
            VisitResponse visit = visitService.getVisitById(id);
            return ResponseEntity.ok(visit);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE Visit by ID
    @PutMapping("/{id}")
    public ResponseEntity<VisitResponse> updateVisit(@PathVariable Long id, @RequestBody VisitRequest request) {
        try {
            VisitResponse updatedVisit = visitService.updateVisit(id, request);
            return ResponseEntity.ok(updatedVisit);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE Visit by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        try {
            visitService.deleteVisit(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
