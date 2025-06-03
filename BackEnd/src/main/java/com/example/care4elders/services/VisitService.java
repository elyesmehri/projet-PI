package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.VisitRequest;
import com.example.care4elders.controllers.DTO.VisitResponse;
import com.example.care4elders.model.Visit;

import java.util.List;

public interface VisitService {

    abstract VisitResponse convertToDto(Visit visit);

    // CREATE
    public abstract VisitResponse createVisit(VisitRequest request);

    // READ (by ID)
    public abstract VisitResponse getVisitById(Long id);

    // READ (all)
    public abstract List<VisitResponse> getAllVisits();

    // UPDATE
    public abstract VisitResponse updateVisit(Long id, VisitRequest request);

    // DELETE
    public abstract void deleteVisit(Long id);

    // Get visits by Doctor ID
    public abstract List<VisitResponse> getVisitsByDoctorId(Long doctorId);

    // Get visits by Patient ID
    public abstract List<VisitResponse> getVisitsByPatientId(Long patientId);
}
