package com.example.care4elders.services;

import com.example.care4elders.controllers.patientRequest;
import com.example.care4elders.controllers.patientRequest;
import com.example.care4elders.model.Appointment;
import com.example.care4elders.model.Patient;
import io.micrometer.observation.ObservationFilter;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> createBulkPatients(List<patientRequest> requests);

    Patient createPatient(patientRequest request);

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
