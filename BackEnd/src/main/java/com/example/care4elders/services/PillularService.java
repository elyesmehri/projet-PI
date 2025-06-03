package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.PillularRequest;
import com.example.care4elders.controllers.DTO.PillularResponse;
import com.example.care4elders.model.Pillular;

import java.util.List;

public interface  PillularService {

    public PillularResponse convertToDto(Pillular pillular);

    // CREATE Pillular (Prescription)
    public abstract PillularResponse createPillular(PillularRequest request);

    // READ Pillular by ID
    public abstract PillularResponse getPillularById(Long id);

    // READ All Pillulars
    public abstract List<PillularResponse> getAllPillulars();

    // UPDATE Pillular by ID
    public abstract PillularResponse updatePillular(Long id, PillularRequest request);

    // DELETE Pillular by ID
    public abstract void deletePillular(Long id);

    public abstract List<PillularResponse> getPillularsByDoctorId(Long doctorId);

    public abstract List<PillularResponse> getPillularsByPatientId(Long patientId);

    public abstract List<PillularResponse> getPillularsByMedicationId(Long medicationId);

    public abstract List<PillularResponse> getPillularsByInsuranceId(Long insuranceId);
}
