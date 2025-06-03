package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.PatientUpdateRequest;
import com.example.care4elders.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {


    Patient save(Patient patient);

    boolean deleteById(Long id);

    Patient updatePassword(long id, String password);

    void deleteAllPatients();

    boolean checkPatientExists(Long id, String password);

    boolean updatePatient(Long id, Patient updated);

    Patient findById(Long id);

    List<Patient> getAllPatients();

    Patient addPatient(Patient patient);

    Optional<Patient> updatePatientFields(Long id, PatientUpdateRequest updateRequest);

    Patient updateAboutme(Long id, String about_me);

    // Ensures the update is committed
    Patient assignFamilyToPatient(Long patientId, Long familyId);

    // Optional: Method to remove a family from a patient (set family to null)
    Patient removeFamilyFromPatient(Long patientId);

    Optional<Long> getPatientIdByPatientName(String familyname);
}
