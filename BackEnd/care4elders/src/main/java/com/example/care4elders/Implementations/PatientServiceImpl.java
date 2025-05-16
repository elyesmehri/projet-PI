package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.patientRequest;
import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Patient;

import com.example.care4elders.controllers.patientRequest;

import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.PatientRepository;
import com.example.care4elders.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Patient> createBulkPatients(List<patientRequest> requests) {
        List<Patient> createdPatients = new ArrayList<>();
        for (patientRequest request : requests) {
            Patient patient = new Patient();
            patient.setPatientName(request.getPatientName());
            patient.setAge(request.getAge());
            patient.setGender(request.isGender());
            patient.setMedicalCondition(request.getMedicalCondition());
            patient.setAddress(request.getAddress());
            patient.setPhoneNumber(request.getPhoneNumber());
            patient.setInsurance(request.getInsurance());
            patient.setPassword(request.getPassword());
            patient.setMedical_state(request.getMedical_state());
            patient.setAbout_me(request.getAbout_me());

            Doctor doctor = doctorRepository.findByDoctorname(request.getDoctorname());
            if (doctor != null) {
                patient.setDoctor(doctor);
            } else {
                throw new RuntimeException("Doctor not found: " + request.getDoctorname() + " for patient: " + request.getPatientName());
            }
            createdPatients.add(patientRepository.save(patient));
        }
        return createdPatients;
    }

    @Override
    public Patient createPatient(patientRequest request) {

        Patient patient = new Patient();
        patient.setPatientName(request.getPatientName());
        patient.setAge(request.getAge());
        patient.setGender(request.isGender());
        patient.setMedicalCondition(request.getMedicalCondition());
        patient.setAddress(request.getAddress());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setInsurance(request.getInsurance());
        patient.setPassword(request.getPassword());
        patient.setMedical_state(request.getMedical_state());
        patient.setAbout_me(request.getAbout_me());

        // Assuming you want to associate the patient with a doctor by name
        Doctor doctor = doctorRepository.findByDoctorname(request.getDoctorname());
        if (doctor != null) {
            patient.setDoctor(doctor);
        } else {
            // Handle the case where the doctor is not found (e.g., throw an exception)
            throw new RuntimeException("Doctor not found: " + request.getDoctorname());
        }

        return patientRepository.save(patient);
    }

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
    public void deletePatientsBulk(List<Long> ids) {

    }

    @Override
    public boolean updatePatient(Long id, Patient updated) {
        Optional<Patient> optional = patientRepository.findById(id);
        if (optional.isPresent()) {

            Patient existing = optional.get();

            existing.setPatientName(updated.getPatientName());
            existing.setAge(updated.getAge());
            existing.setAddress(updated.getAddress());
            existing.setGender(updated.getGender());
            existing.setDoctor(updated.getDoctor());
            existing.setInsurance(updated.getInsurance());
            existing.setMedicalCondition(updated.getMedicalCondition());
            existing.setPassword(updated.getPassword());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setMedical_state(updated.getMedical_state());
            existing.setAbout_me(updated.getAbout_me());

            patientRepository.save(existing);

            return true;
        }
        return false;
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
    public List<Patient> getPatientsByDoctor(String doctorname) {
        return patientRepository.findByDoctor_Doctorname(doctorname);
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

    @Override
    public Long getPatientCountForDoctor(Long doctorId) {
        return patientRepository.countByDoctorId(doctorId);
    }
}
