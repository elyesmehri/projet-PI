package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.CheckPatientRequest;
import com.example.care4elders.controllers.DTO.PatientRequest;
import com.example.care4elders.controllers.DTO.PatientUpdateRequest;
import com.example.care4elders.controllers.DTO.VisitResponse;
import com.example.care4elders.model.Patient;

import com.example.care4elders.services.*;

import com.example.care4elders.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final VisitService visitService;

    @PostMapping("/create")
    public ResponseEntity<Patient> addPatient (@RequestBody PatientRequest request) {

        Patient patient = new Patient();

        patient.setPatientName(request.getPatientName());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setMedical_state(request.getMedical_state());
        patient.setGender(request.getGender());
        patient.setPassword(request.getPassword());
        patient.setAddress(request.getAddress());
        patient.setAge(request.getAge());

        patientService.save(patient);

        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PatchMapping("/{id}") // Maps to PUT /patient/{id}
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id, // Get the patient ID from the URL path
            @RequestBody PatientUpdateRequest updateRequest) { // Get the update data from the request body

        Optional<Patient> updatedPatientOpt = patientService.updatePatientFields(id, updateRequest);

        if (updatedPatientOpt.isPresent()) {
            return ResponseEntity.ok(updatedPatientOpt.get()); // Return the updated patient object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient with ID " + id + " not found.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> allPatients = patientService.getAllPatients() ;
        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = Optional.ofNullable(patientService.findById(id));
        return patient.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
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

    // http://localhost:8082/patient/password/change/1?password=MyNewSecurePassword123
    @PatchMapping("password/{updatepass}/{id}")
    public ResponseEntity<?> updatePassword(
            @PathVariable String updatepass,
            @PathVariable long id,
            @RequestParam String password) {
        try {
            Optional<Patient> updated = Optional.ofNullable(patientService.updatePassword(id,password));
            return ResponseEntity.ok("Password changed to " + password);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Patient not found");
        }
    }

    @PostMapping("/auth") // This will be your endpoint for checking existence
    public ResponseEntity<Boolean> checkPatientExists(@RequestBody CheckPatientRequest request) {
        boolean exists = patientService.checkPatientExists(request.getId(), request.getPassword());
        return ResponseEntity.ok(exists); // Returns true or false
    }


    // curl -X PATCH "http://localhost:8082/patient/1/advice"  -H "Content-Type: application/json" -d '1500.75'
    @PatchMapping("/{id}/about_me")
    public ResponseEntity<Patient> updatePatientAdvice(
            @PathVariable Long id,
            @RequestBody String about_me) { // The new advice string comes in the request body

        try {
            Patient updatedPatient = patientService.updateAboutme(id, about_me);
            return ResponseEntity.ok(updatedPatient); // Return 200 OK with the updated patient
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if patient doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Generic error
        }
    }

    @PatchMapping("/{patientId}/assignFamily/{familyId}")
    public ResponseEntity<Patient> assignFamilyToPatient(
            @PathVariable Long patientId,
            @PathVariable Long familyId) {
        try {
            Patient updatedPatient = patientService.assignFamilyToPatient(patientId, familyId);
            return ResponseEntity.ok(updatedPatient); // Return 200 OK with the updated patient
        } catch (EntityNotFoundException e) {
            // Return 404 Not Found if either Patient or Family doesn't exist
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Catch any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{patientId}/removeFamily") // Or DELETE, depending on REST semantics
    public ResponseEntity<Patient> removeFamilyFromPatient(@PathVariable Long patientId) {
        try {
            Patient updatedPatient = patientService.removeFamilyFromPatient(patientId);
            return ResponseEntity.ok(updatedPatient);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{patientId}/visits")
    public ResponseEntity<List<VisitResponse>> getPatientVisits(@PathVariable Long patientId) {
        try {
            List<VisitResponse> visits = visitService.getVisitsByPatientId(patientId);
            return ResponseEntity.ok(visits); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if patient doesn't exist
        }
    }

    @GetMapping("/id-by-name")
    public ResponseEntity<Long> getPatientIdByName(@RequestParam("name") String patientname) {
        Optional<Long> PatientId = patientService.getPatientIdByPatientName(patientname);

        if (PatientId.isPresent()) {
            return ResponseEntity.ok(PatientId.get()); // Return 200 OK with the ID
        } else {
            // Return 404 Not Found if no doctor with that name is found
            return ResponseEntity.notFound().build();
        }
    }
}

