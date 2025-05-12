package com.example.care4elders.services;

import com.example.care4elders.model.Patient;
import io.micrometer.observation.ObservationFilter;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);

    Patient addPatient(Patient patient);

    List<Patient> addPatientsBulk(List<Patient> patients);

    Patient updatePatient(Long id, Patient updatedPatient);

    void deletePatient(Long id);

    void deletePatientsBulk(List<Long> ids);

    boolean authenticatePatient(String patientName, String password);

    List<Patient> getPatientsByDoctor(String doctorName);

//    Optional<Patient> getPatientByName(String name);

    void deleteAllPatients();

    public String getPatientGenderByName(String name);

    boolean updatePatientPassword(String patientName, String newPassword);

    boolean updateMedicalStateByName(String patientName, int medical_state);

    Patient getPatientByName(String name);
}
