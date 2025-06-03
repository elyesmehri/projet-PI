package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.DTO.InsuranceRequest;
import com.example.care4elders.controllers.DTO.InsuranceResponse;
import com.example.care4elders.controllers.DTO.OperationResponse;

import com.example.care4elders.controllers.DTO.MedicationResponse;

import com.example.care4elders.model.Insurance;
import com.example.care4elders.model.Medication;
import com.example.care4elders.model.Operation;
import com.example.care4elders.repository.InsuranceRepository;
import com.example.care4elders.repository.MedicationRepository;
import com.example.care4elders.repository.OperationRepository;
import com.example.care4elders.services.InsuranceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final MedicationRepository medicationRepository; // Inject
    private final OperationRepository operationRepository;   // Inject

    // Helper method to convert Medication Entity to a simple DTO for InsuranceResponse
    @Override
    public MedicationResponse convertMedicationToSimpleDto(Medication medication) {
        MedicationResponse dto = new MedicationResponse();
        dto.setId(medication.getId());
        dto.setMedicationName(medication.getMedicationName());
        dto.setCategory(medication.getCategory());
        return dto;
    }

    // Helper method to convert Operation Entity to a simple DTO for InsuranceResponse
    @Override
    public OperationResponse convertOperationToSimpleDto(Operation operation) {
        OperationResponse dto = new OperationResponse();
        dto.setId(operation.getId());
        dto.setOperationName(operation.getOperationName());
        dto.setOperationCode(operation.getOperationCode());
        dto.setCategory(operation.getCategory());
        return dto;
    }

    @Override
    public InsuranceResponse convertToDto(Insurance insurance) {
        InsuranceResponse dto = new InsuranceResponse();
        dto.setId(insurance.getId());
        dto.setProviderName(insurance.getProviderName());
        dto.setPolicyNumber(insurance.getPolicyNumber());
        dto.setCoverageDetails(insurance.getCoverageDetails());

        dto.setNumberOfLinkedPrescriptions(insurance.getLinkedPrescriptions().size());

        // Populate supported medications
        dto.setSupportedMedications(insurance.getSupportedMedications().stream()
                .map(this::convertMedicationToSimpleDto)
                .collect(Collectors.toList()));

        // Populate supported operations
        dto.setSupportedOperations(insurance.getSupportedOperations().stream()
                .map(this::convertOperationToSimpleDto)
                .collect(Collectors.toList()));

        return dto;
    }

    // CREATE Insurance
    @Override
    public InsuranceResponse createInsurance(InsuranceRequest request) {
        Insurance insurance = new Insurance();
        insurance.setProviderName(request.getProviderName());
        insurance.setPolicyNumber(request.getPolicyNumber());
        insurance.setCoverageDetails(request.getCoverageDetails());

        // Set supported medications
        if (request.getSupportedMedicationIds() != null && !request.getSupportedMedicationIds().isEmpty()) {
            List<Medication> medications = medicationRepository.findAllById(request.getSupportedMedicationIds());
            if (medications.size() != request.getSupportedMedicationIds().size()) {
                throw new EntityNotFoundException("One or more supported medication IDs not found.");
            }
            insurance.setSupportedMedications(medications);
        } else {
            insurance.setSupportedMedications(new ArrayList<>()); // Initialize empty list
        }

        // Set supported operations
        if (request.getSupportedOperationIds() != null && !request.getSupportedOperationIds().isEmpty()) {
            List<Operation> operations = operationRepository.findAllById(request.getSupportedOperationIds());
            if (operations.size() != request.getSupportedOperationIds().size()) {
                throw new EntityNotFoundException("One or more supported operation IDs not found.");
            }
            insurance.setSupportedOperations(operations);
        } else {
            insurance.setSupportedOperations(new ArrayList<>()); // Initialize empty list
        }

        Insurance savedInsurance = insuranceRepository.save(insurance);
        return convertToDto(savedInsurance);
    }

    // READ Insurance by ID
    @Override
    public InsuranceResponse getInsuranceById(Long id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + id));
        return convertToDto(insurance);
    }

    // READ All Insurances
    @Override
    public List<InsuranceResponse> getAllInsurances() {
        return insuranceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE Insurance
    @Override
    public InsuranceResponse updateInsurance(Long id, InsuranceRequest request) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + id));

        insurance.setProviderName(request.getProviderName());
        insurance.setPolicyNumber(request.getPolicyNumber());
        insurance.setCoverageDetails(request.getCoverageDetails());

        // Update supported medications
        if (request.getSupportedMedicationIds() != null) { // If null, assume no change or explicitly clear
            List<Medication> medications = medicationRepository.findAllById(request.getSupportedMedicationIds());
            if (medications.size() != request.getSupportedMedicationIds().size()) {
                throw new EntityNotFoundException("One or more supported medication IDs not found for update.");
            }
            insurance.setSupportedMedications(medications);
        } else {
            insurance.setSupportedMedications(new ArrayList<>()); // Clear if list not provided in request
        }

        // Update supported operations
        if (request.getSupportedOperationIds() != null) { // If null, assume no change or explicitly clear
            List<Operation> operations = operationRepository.findAllById(request.getSupportedOperationIds());
            if (operations.size() != request.getSupportedOperationIds().size()) {
                throw new EntityNotFoundException("One or more supported operation IDs not found for update.");
            }
            insurance.setSupportedOperations(operations);
        } else {
            insurance.setSupportedOperations(new ArrayList<>()); // Clear if list not provided in request
        }

        Insurance updatedInsurance = insuranceRepository.save(insurance);
        return convertToDto(updatedInsurance);
    }

    // DELETE Insurance
    @Override
    public void deleteInsurance(Long id) {
        if (!insuranceRepository.existsById(id)) {
            throw new EntityNotFoundException("Insurance not found with ID: " + id);
        }

        insuranceRepository.deleteById(id);
    }

    // Custom finders
    @Override
    public InsuranceResponse getInsuranceByPolicyNumber(String policyNumber) {
        Insurance insurance = insuranceRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with policy number: " + policyNumber));
        return convertToDto(insurance);
    }

    @Override
    public InsuranceResponse addSupportedMedication(Long insuranceId, Long medicationId) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with ID: " + medicationId));

        if (insurance.getSupportedMedications().contains(medication)) {
            throw new IllegalStateException("Medication with ID " + medicationId + " is already supported by insurance " + insuranceId);
        }

        insurance.getSupportedMedications().add(medication);
        Insurance updatedInsurance = insuranceRepository.save(insurance); // Persist the change
        return convertToDto(updatedInsurance);
    }

    @Override
    public InsuranceResponse removeSupportedMedication(Long insuranceId, Long medicationId) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with ID: " + medicationId));

        if (!insurance.getSupportedMedications().contains(medication)) {
            throw new IllegalStateException("Medication with ID " + medicationId + " is not currently supported by insurance " + insuranceId);
        }

        insurance.getSupportedMedications().remove(medication);
        Insurance updatedInsurance = insuranceRepository.save(insurance); // Persist the change
        return convertToDto(updatedInsurance);
    }

    @Override
    public InsuranceResponse addSupportedOperation(Long insuranceId, Long operationId) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found with ID: " + operationId));

        if (insurance.getSupportedOperations().contains(operation)) {
            throw new IllegalStateException("Operation with ID " + operationId + " is already supported by insurance " + insuranceId);
        }

        insurance.getSupportedOperations().add(operation);
        Insurance updatedInsurance = insuranceRepository.save(insurance); // Persist the change
        return convertToDto(updatedInsurance);
    }

    @Override
    public InsuranceResponse removeSupportedOperation(Long insuranceId, Long operationId) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found with ID: " + operationId));

        if (!insurance.getSupportedOperations().contains(operation)) {
            throw new IllegalStateException("Operation with ID " + operationId + " is not currently supported by insurance " + insuranceId);
        }

        insurance.getSupportedOperations().remove(operation);
        Insurance updatedInsurance = insuranceRepository.save(insurance); // Persist the change
        return convertToDto(updatedInsurance);
    }
}

