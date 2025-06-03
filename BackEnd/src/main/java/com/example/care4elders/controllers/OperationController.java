package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.OperationRequest;
import com.example.care4elders.controllers.DTO.OperationResponse;

import com.example.care4elders.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/create")
    public ResponseEntity<OperationResponse> createOperation(@RequestBody OperationRequest request) {
        OperationResponse newOperation = operationService.createOperation(request);
        return new ResponseEntity<>(newOperation, HttpStatus.CREATED); // 201 Created
    }

    // READ All Operations
    @GetMapping("/all")
    public ResponseEntity<List<OperationResponse>> getAllOperations() {
        List<OperationResponse> operations = operationService.getAllOperations();
        return ResponseEntity.ok(operations); // 200 OK
    }

    // READ Operation by ID
    @GetMapping("/{id}")
    public ResponseEntity<OperationResponse> getOperationById(@PathVariable Long id) {
        try {
            OperationResponse operation = operationService.getOperationById(id);
            return ResponseEntity.ok(operation); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // UPDATE Operation by ID
    @PutMapping("/{id}")
    public ResponseEntity<OperationResponse> updateOperation(@PathVariable Long id, @RequestBody OperationRequest request) {
        try {
            OperationResponse updatedOperation = operationService.updateOperation(id, request);
            return ResponseEntity.ok(updatedOperation); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // DELETE Operation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Long id) {
        try {
            operationService.deleteOperation(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
