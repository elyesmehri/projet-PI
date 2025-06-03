package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.InsuranceRequest;
import com.example.care4elders.controllers.DTO.InsuranceResponse;
import com.example.care4elders.controllers.DTO.MedicationResponse;
import com.example.care4elders.controllers.DTO.OperationResponse;
import com.example.care4elders.model.Insurance;
import com.example.care4elders.model.Medication;
import com.example.care4elders.model.Operation;

import java.util.List;

public interface InsuranceService {
    // Helper method to convert Medication Entity to a simple DTO for InsuranceResponse
    MedicationResponse convertMedicationToSimpleDto(Medication medication);

    // Helper method to convert Operation Entity to a simple DTO for InsuranceResponse
    OperationResponse convertOperationToSimpleDto(Operation operation);

    // Helper method to convert Entity to Response DTO
    InsuranceResponse convertToDto(Insurance insurance);

    // CREATE Insurance
    InsuranceResponse createInsurance(InsuranceRequest request);

    // READ Insurance by ID
    InsuranceResponse getInsuranceById(Long id);

    // READ All Insurances
    List<InsuranceResponse> getAllInsurances();

    // UPDATE Insurance
    InsuranceResponse updateInsurance(Long id, InsuranceRequest request);

    // DELETE Insurance
    void deleteInsurance(Long id);

    // Custom finders
    InsuranceResponse getInsuranceByPolicyNumber(String policyNumber);

    InsuranceResponse addSupportedMedication(Long insuranceId, Long medicationId);

    InsuranceResponse removeSupportedMedication(Long insuranceId, Long medicationId);

    InsuranceResponse addSupportedOperation(Long insuranceId, Long operationId);

    InsuranceResponse removeSupportedOperation(Long insuranceId, Long operationId);

    // public abstract void addDoctorToInsurance(Long insuranceId, Long doctorId);
}
