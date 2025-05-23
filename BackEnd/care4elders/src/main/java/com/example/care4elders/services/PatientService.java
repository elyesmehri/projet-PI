package com.example.care4elders.services;

import com.example.care4elders.controllers.PatientRequest;
import com.example.care4elders.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> createBulkPatients(List<PatientRequest> requests);

    Patient createPatient(PatientRequest request);

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);

    Patient addPatient(Patient patient);


    boolean updatePatient(Long id, Patient updated);

    void deletePatient(Long id);

    void deletePatientsBulk(List<Long> ids);

    boolean authenticatePatient(String patientName, String password);

    List<Patient> getPatientsByDoctor(String doctorName);

    void deleteAllPatients();

    Patient updatePatient(Patient patient);

    public String getPatientGenderByName(String name);

    boolean updatePatientPassword(String patientName, String newPassword);

    Patient getPatientByName(String name);

    boolean updateMedicalStateByName(String patientName, String medical_state);

    Long getPatientCountForDoctor(Long doctorId);

}
