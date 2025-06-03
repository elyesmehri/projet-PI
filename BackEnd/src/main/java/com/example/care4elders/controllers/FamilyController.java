package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.AppointmentResponse;
import com.example.care4elders.controllers.DTO.CheckFamilyRequest;
import com.example.care4elders.controllers.DTO.FamilyRequest;
import com.example.care4elders.controllers.DTO.FamilyUpdateRequest;
import com.example.care4elders.model.Family;

import com.example.care4elders.services.*;
import com.example.care4elders.services.FamilyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/family")
@RequiredArgsConstructor
public class FamilyController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final FamilyService familyService;
    private final AppointmentService appointmentService;


    @PostMapping("/create")
    public ResponseEntity<String> addFamily (@RequestBody FamilyRequest request) {

        Family family = new Family();

        family.setFamilyname(request.getFamilyname());
        family.setPhoneNumber(request.getPhoneNumber());
        family.setRelative(request.getRelative());
        family.setRelationship(request.getRelationship());
        family.setInvest(request.getInvest());
        family.setPassword(request.getPassword());
        family.setInsurance(request.getInsurance());
        family.setAdvice(request.getAdvice());
        family.setAddress(request.getAddress());
        family.setLogin(request.getLogin());

        familyService.save(family);

        return ResponseEntity.status(HttpStatus.CREATED).body("Family added successfully with ID: " + family.getId());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Family>> getAllFamillies() {
        List<Family> allFamillies = familyService.getAllFamilies();
        return new ResponseEntity<>(allFamillies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Family> getFamilyById(@PathVariable Long id) {
        Optional<Family> family = familyService.getFamilyById(id);
        return family.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllFamilys() {
        try {
            familyService.deleteAllFamilies();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFamilyById(@PathVariable Long id) {
        boolean deleted = familyService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Family deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found.");
        }
    }

    // http://localhost:8082/family/password/change/1?password=MyNewSecurePassword123
    @PatchMapping("password/{updatepass}/{id}")
    public ResponseEntity<?> updatePassword(
            @PathVariable String updatepass,
            @PathVariable long id,
            @RequestParam String password) {
        try {
            Optional<Family> updated = Optional.ofNullable(familyService.updatePassword(id,password));
            return ResponseEntity.ok("Password changed to " + password);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Family not found");
        }
    }

    @PostMapping("/auth") // This will be your endpoint for checking existence
    public ResponseEntity<Boolean> checkFamilyExists(@RequestBody CheckFamilyRequest request) {
        boolean exists = familyService.checkFamilyExists(request.getId(), request.getRelative(), request.getPassword());
        return ResponseEntity.ok(exists); // Returns true or false
    }

    // curl -X PATCH "http://localhost:8082/family/1/invest"  -H "Content-Type: application/json" -d '1500.75'
    @PatchMapping("/{familyId}/invest")
    public ResponseEntity<Family> updateFamilyInvestissement(
            @PathVariable Long familyId,
            @RequestBody float newInvest) { // The new investment value comes in the request body

        try {
            Family updatedFamily = familyService.updateInvestissement(familyId, newInvest);
            return ResponseEntity.ok(updatedFamily); // Return 200 OK with the updated family
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if family doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Generic error
        }
    }

    @PutMapping("/{id}") // Maps to PUT /family/{id}
    public ResponseEntity<?> updateFamily(
            @PathVariable Long id, // Get the family ID from the URL path
            @RequestBody FamilyUpdateRequest updateRequest) { // Get the update data from the request body

        Optional<Family> updatedFamilyOpt = familyService.updateFamilyFields(id, updateRequest);

        if (updatedFamilyOpt.isPresent()) {
            return ResponseEntity.ok(updatedFamilyOpt.get()); // Return the updated family object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family with ID " + id + " not found.");
        }
    }

    // curl -X PATCH "http://localhost:8082/family/1/advice"  -H "Content-Type: application/json" -d '1500.75'
    @PatchMapping("/{familyId}/advice")
    public ResponseEntity<Family> updateFamilyAdvice(
            @PathVariable Long familyId,
            @RequestBody String newAdvice) { // The new advice string comes in the request body

        try {
            Family updatedFamily = familyService.updateAdvice(familyId, newAdvice);
            return ResponseEntity.ok(updatedFamily); // Return 200 OK with the updated family
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if family doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Generic error
        }
    }

    @PostMapping("/login/{familyId}")
    public ResponseEntity<Family> incrementLogin(@PathVariable Long familyId) {
        Family updatedFamily = familyService.incrementLogin(familyId);
        if (updatedFamily != null) {
            return new ResponseEntity<>(updatedFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/resetlogin/{familyId}")
    public ResponseEntity<Family> resetLogin(@PathVariable Long familyId) {
        Family updatedFamily = familyService.resetLogin(familyId);
        if (updatedFamily != null) {
            return new ResponseEntity<>(updatedFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{familyId}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getFamilyAppointments(@PathVariable Long familyId) {
        try {
            List<AppointmentResponse> appointments = appointmentService.getAppointmentsByFamilyId(familyId);
            return ResponseEntity.ok(appointments); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if family doesn't exist
        }
    }

    @GetMapping("/id-by-name")
    public ResponseEntity<Long> getFamilyIdByName(@RequestParam("name") String familyname) {
        Optional<Long> FamilyId = familyService.getFamilyIdByFamilyName(familyname);

        if (FamilyId.isPresent()) {
            return ResponseEntity.ok(FamilyId.get()); // Return 200 OK with the ID
        } else {
            // Return 404 Not Found if no doctor with that name is found
            return ResponseEntity.notFound().build();
        }
    }



}
