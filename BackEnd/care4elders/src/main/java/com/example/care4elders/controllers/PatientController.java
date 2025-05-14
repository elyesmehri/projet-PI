package com.example.care4elders.controllers;

import com.example.care4elders.model.Patient;
import com.example.care4elders.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/bulk")
    public ResponseEntity<List<Patient>> createBulkPatient(@RequestBody List<Patient> patients) {
        try {
            List<Patient> saved = patientService.addPatientsBulk(patients);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{patientName}")
    public ResponseEntity<Patient> getPatientByName(@PathVariable String patientName) {
        try {
            Patient patient = patientService.getPatientByName(patientName);
            return ResponseEntity.ok(patient);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NonUniqueResultException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Patient> getAllPatient() {
        return patientService.getAllPatients();
    }

    @PostMapping("/add")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @PutMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(id, updatedPatient);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody PatientPasswordUpdateRequest request) {
        boolean updated = patientService.updatePatientPassword(request.getPatientName(), request.getPassword());

        if (updated) {
            return ResponseEntity.ok("Password updated successfully ✅");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Patient not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> authenticatePatient(@RequestBody Map<String, String> payload) {
        String name = payload.get("patientName");
        String password = payload.get("password");
        boolean result = patientService.authenticatePatient(name, password);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBulkPatient(@RequestBody List<Long> ids) {
        try {
            patientService.deletePatientsBulk(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllPatients() {
        try {
            patientService.deleteAllPatients();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/gender/{name}")
    public ResponseEntity<String> getPatientGender(@RequestParam String name) {
        try {
            String gender = patientService.getPatientGenderByName(name);
            return ResponseEntity.ok(gender);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/medicalstate")
    public ResponseEntity<String> updatePatientMedicalStateByName(
            @RequestParam("patientName") String patientName,
            @RequestParam("medical_state") String medical_state) {

        try {
            boolean updated = patientService.updateMedicalStateByName(patientName, medical_state);
            if (updated) {
                return new ResponseEntity<>("Medical state updated successfully for patient: " + patientName, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Patient not found with name: " + patientName, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating medical state: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
