package com.example.care4elders.controllers;

import com.example.care4elders.model.Medication;
import com.example.care4elders.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/medication")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/bulk")
    public ResponseEntity<List<Medication>> createBulkMedication(@RequestBody List<Medication> medications) {
        try {
            List<Medication> saved = medicationService.addMedicationsBulk(medications);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        return medicationService.getMedicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Medication> getAllMedication() {
        return medicationService.getAllMedications();
    }

    @PostMapping("/add")
    public Medication addMedication(@RequestBody Medication medication) {
        return medicationService.addMedication(medication);
    }

    @PutMapping("/update/{id}")
    public Medication updateMedication(@PathVariable Long id, @RequestBody Medication updatedMedication) {
        return medicationService.updateMedication(id, updatedMedication);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBulkMedication(@RequestBody List<Long> ids) {
        try {
            medicationService.deleteMedicationsBulk(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{medicationName}")
    public ResponseEntity<?> getMedicationByName(@PathVariable String medicationName) {
        Medication medication = medicationService.findByName(medicationName);
        if (medication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medication not found with name: " + medicationName);
        }
        return ResponseEntity.ok(medication);
    }
}
