package com.example.care4elders.Implementations;


import com.example.care4elders.controllers.DTO.VisitRequest;
import com.example.care4elders.controllers.DTO.VisitResponse;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Patient;
import com.example.care4elders.model.Visit;

import com.example.care4elders.services.VisitService;

import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.PatientRepository;
import com.example.care4elders.repository.VisitRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public VisitResponse convertToDto(Visit visit) {
        VisitResponse dto = new VisitResponse();
        dto.setId(visit.getId());
        dto.setVisitDate(visit.getVisitDate());
        dto.setAssisted(visit.isAssisted());

        if (visit.getDoctor() != null) {
            dto.setDoctorId(visit.getDoctor().getId());
            dto.setDoctorName(visit.getDoctor().getDoctorname()); // Assuming Doctor has getDoctorname()
        }
        if (visit.getPatient() != null) {
            dto.setPatientId(visit.getPatient().getId());
            dto.setPatientName(visit.getPatient().getPatientName()); // Assuming Patient has getPatientName()
        }
        return dto;
    }

    // CREATE
    @Override
    public VisitResponse createVisit(VisitRequest request) {
        Visit visit = new Visit();

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));

        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setVisitDate(request.getVisitDate());
        visit.setAssisted(request.isAssisted());

        Visit savedVisit = visitRepository.save(visit);
        return convertToDto(savedVisit);
    }

    // READ (by ID)
    @Override
    public VisitResponse getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found with ID: " + id));
        return convertToDto(visit);
    }

    // READ (all)
    @Override
    public List<VisitResponse> getAllVisits() {
        return visitRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public VisitResponse updateVisit(Long id, VisitRequest request) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found with ID: " + id));

        // Update relationships only if IDs are provided and changed
        if (request.getDoctorId() != null && (visit.getDoctor() == null || !request.getDoctorId().equals(visit.getDoctor().getId()))) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
            visit.setDoctor(doctor);
        }
        if (request.getPatientId() != null && (visit.getPatient() == null || !request.getPatientId().equals(visit.getPatient().getId()))) {
            Patient patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));
            visit.setPatient(patient);
        }

        // Update scalar fields
        visit.setVisitDate(request.getVisitDate());
        visit.setAssisted(request.isAssisted());

        Visit updatedVisit = visitRepository.save(visit);
        return convertToDto(updatedVisit);
    }

    // DELETE
    @Override
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new EntityNotFoundException("Visit not found with ID: " + id);
        }
        visitRepository.deleteById(id);
    }

    // Get visits by Doctor ID
    @Override
    public List<VisitResponse> getVisitsByDoctorId(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return visitRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get visits by Patient ID
    @Override
    public List<VisitResponse> getVisitsByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Patient not found with ID: " + patientId);
        }
        return visitRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
