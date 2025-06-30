package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.*;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import com.example.care4elders.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {


    private final InsuranceService insuranceService;
    private final PillularService pillularService;

    // CREATE Insurance
    @PostMapping("/create")
    public ResponseEntity<InsuranceResponse> createInsurance(@RequestBody InsuranceRequest request) {
        try {
            InsuranceResponse newInsurance = insuranceService.createInsurance(request);
            return new ResponseEntity<>(newInsurance, HttpStatus.CREATED); // 201 Created
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request if related entities not found
        }
    }

    // READ All Insurances
    @GetMapping("/all")
    public ResponseEntity<List<InsuranceResponse>> getAllInsurances() {
        List<InsuranceResponse> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances); // 200 OK
    }

    // READ Insurance by ID
    @GetMapping("/{id}")
    public ResponseEntity<InsuranceResponse> getInsuranceById(@PathVariable Long id) {
        try {
            InsuranceResponse insurance = insuranceService.getInsuranceById(id);
            return ResponseEntity.ok(insurance); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // UPDATE Insurance by ID
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponse> updateInsurance(@PathVariable Long id, @RequestBody InsuranceRequest request) {
        try {
            InsuranceResponse updatedInsurance = insuranceService.updateInsurance(id, request);
            return ResponseEntity.ok(updatedInsurance); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // DELETE Insurance by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        try {
            insuranceService.deleteInsurance(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // Get linked Pillulars (Prescriptions)
    @GetMapping("/{insuranceId}/pillulars")
    public ResponseEntity<List<PillularResponse>> getLinkedPillulars(@PathVariable Long insuranceId) {
        try {
            List<PillularResponse> pillulars = pillularService.getPillularsByInsuranceId(insuranceId);
            return ResponseEntity.ok(pillulars);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{insuranceId}/medications/{medicationId}")
    public ResponseEntity<InsuranceResponse> addMedicationToInsurance(
            @PathVariable Long insuranceId,
            @PathVariable Long medicationId) {
        try {
            InsuranceResponse updatedInsurance = insuranceService.addSupportedMedication(insuranceId, medicationId);
            return ResponseEntity.ok(updatedInsurance); // 200 OK with updated resource
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 if insurance or medication not found
        } catch (IllegalStateException e) {
            // This catches if the medication is already linked
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }
    }

    @DeleteMapping("/{insuranceId}/medications/{medicationId}")
    public ResponseEntity<Void> removeMedicationFromInsurance(
            @PathVariable Long insuranceId,
            @PathVariable Long medicationId) {
        try {
            insuranceService.removeSupportedMedication(insuranceId, medicationId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 if insurance or medication not found
        } catch (IllegalStateException e) {
            // This catches if the medication is not linked
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Could be 404 if not found to remove
        }
    }

    @PostMapping("/{insuranceId}/operations/{operationId}")
    public ResponseEntity<InsuranceResponse> addOperationToInsurance(
            @PathVariable Long insuranceId,
            @PathVariable Long operationId) {
        try {
            InsuranceResponse updatedInsurance = insuranceService.addSupportedOperation(insuranceId, operationId);
            return ResponseEntity.ok(updatedInsurance); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        }
    }

    @DeleteMapping("/{insuranceId}/operations/{operationId}")
    public ResponseEntity<Void> removeOperationFromInsurance(
            @PathVariable Long insuranceId,
            @PathVariable Long operationId) {
        try {
            insuranceService.removeSupportedOperation(insuranceId, operationId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
