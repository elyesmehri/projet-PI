package com.example.care4elders.controllers;

import com.example.care4elders.Implementations.DoctorServiceImpl;
import com.example.care4elders.model.Appointment;
import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Family;
import com.example.care4elders.model.Patient;

import com.example.care4elders.services.*;

import com.example.care4elders.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.care4elders.controllers.patientRequest;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @PostMapping("/bulk")
    public ResponseEntity<List<Patient>> createBulkPatients(@RequestBody List<patientRequest> patientRequests) {
        List<Patient> createdPatients = patientService.createBulkPatients(patientRequests);
        return new ResponseEntity<>(createdPatients, HttpStatus.CREATED);
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
    public ResponseEntity<Patient> createPatient(@RequestBody patientRequest request) {
        Patient newPatient = patientService.createPatient(request);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody Patient updated) {
        boolean result = patientService.updatePatient(id, updated);
        if (result) {
            return ResponseEntity.ok("Patient Data updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
        }
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

    @PatchMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody patientRequest request) {
        try {
            Optional<Patient> existingPatient = patientService.getPatientById(id);
            if (existingPatient.isPresent()) {
                Patient patientToUpdate = existingPatient.get();

                // Update only the fields that are not null in the request
                if (request.getPatientName() != null) {
                    patientToUpdate.setPatientName(request.getPatientName());
                }
                if (request.getAge() != 0) {
                    patientToUpdate.setAge(request.getAge());
                }

                patientToUpdate.setGender(request.getGender());

                if (request.getMedicalCondition() != null) {
                    patientToUpdate.setMedicalCondition(request.getMedicalCondition());
                }
                //Doctor
                if(request.getDoctorname() != null){

                    Doctor doctor = doctorService.findByName(request.getDoctorname()); //  You need to implement this method.
                    patientToUpdate.setDoctor(doctor);
                }

                if (request.getAddress() != null) {
                    patientToUpdate.setAddress(request.getAddress());
                }
                if (request.getPhoneNumber() != null) {
                    patientToUpdate.setPhoneNumber(request.getPhoneNumber());
                }
                if (request.getInsurance() != null) {
                    patientToUpdate.setInsurance(request.getInsurance());
                }
                if (request.getPassword() != null) {
                    patientToUpdate.setPassword(request.getPassword());
                }
                if (request.getMedical_state() != null) {
                    patientToUpdate.setMedical_state(request.getMedical_state());
                }
                if (request.getAbout_me() != null) {
                    patientToUpdate.setAbout_me(request.getAbout_me());
                }

                Patient updatedPatient = patientService.updatePatient(patientToUpdate);
                return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
