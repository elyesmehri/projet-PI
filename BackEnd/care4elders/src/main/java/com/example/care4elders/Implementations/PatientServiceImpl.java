package com.example.care4elders.Implementations;

import com.example.care4elders.model.Patient;

import com.example.care4elders.repository.PatientRepository;
import com.example.care4elders.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> addPatientsBulk(List<Patient> patients) {
        return patientRepository.saveAll(patients);
    }

    @Override
    public Patient updatePatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setPatientName(updatedPatient.getPatientName());
                    patient.setId(updatedPatient.getId());
                    return patientRepository.save(patient);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }

    public Patient getPatientByName(String patientName) {
        List<Patient> patients = patientRepository.findByPatientName(patientName);

        if (patients.isEmpty()) {
            throw new EntityNotFoundException("Patient not found with name: " + patientName);
        } else if (patients.size() == 1) {
            return patients.get(0); // Return the single patient found
        } else {
            // Handle the case where multiple patients have the same name
            // You need to decide what the desired behavior is:
            // 1. Return the first patient in the list:
            return patients.get(0);
            // 2. Throw an exception indicating multiple results:
            // throw new NonUniqueResultException("Multiple patients found with the name: " + patientName);
            // 3. Implement some logic to choose which patient to return
            //    (e.g., based on some other criteria).
        }
    }


    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }


    @Override
    public boolean authenticatePatient(String patientName, String password) {
        List<Patient> patients = patientRepository.findByPatientName(patientName);

        for (Patient patient : patients) {
            if (patient.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deletePatientsBulk(List<Long> ids) {
        patientRepository.deleteAllById(ids);
    }


    @Override
    public List<Patient> getPatientsByDoctor(String doctorName) {
        return patientRepository.findByDoctor(doctorName);
    }

    @Override
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    @Override
    public String getPatientGenderByName(String name) {
        List<Patient> patients = patientRepository.findByPatientName(name);

        if (patients.isEmpty()) {
            throw new EntityNotFoundException("Patient not found");
        }

        // Assume we take the first match
        Patient patient = patients.get(0);
        return patient.isGender() ? "Male" : "Female";
    }

    @Override
    public boolean updatePatientPassword(String patientName, String newPassword) {
        List<Patient> patients = patientRepository.findByPatientName(patientName);

        if (!patients.isEmpty()) {
            Patient patient = patients.get(0);
            patient.setPassword(newPassword);
            patientRepository.save(patient);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateMedicalStateByName(String patientName, String medical_state) {
        List<Patient> patients = patientRepository.findByPatientName(patientName);
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                patient.setMedical_state(medical_state);
                patientRepository.save(patient);
            }
            return true; // Return true if at least one patient was updated
        }
        return false; // Return false if no patient was found with that name
    }



}
