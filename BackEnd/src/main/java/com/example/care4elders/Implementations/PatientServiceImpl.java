package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.DTO.PatientUpdateRequest;
import com.example.care4elders.model.Family;
import com.example.care4elders.model.Patient;

import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.FamilyRepository;
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
    private final DoctorRepository doctorRepository;
    private final FamilyRepository familyRepository;

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public boolean deleteById(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Patient updatePassword(long id, String password) {
        Optional<Patient> optionalPatient = patientRepository.findById(id); // Use findById, the standard JPA method

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setPassword(password);
            return patientRepository.save(patient);
        } else {

            return null; // Or throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
    }

    @Override
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }


    @Override
    public boolean checkPatientExists(Long id, String password) {
        Optional<Patient> patientOptional = patientRepository.findById(id);

        if (patientOptional.isPresent()) {

            Patient patient = patientOptional.get();

            return patient.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public boolean updatePatient(Long id, Patient updated) {

        Optional<Patient> optional = patientRepository.findById(id);

        if (optional.isPresent()) {

            Patient existing = optional.get();

            existing.setPatientName(updated.getPatientName());
            existing.setPassword(updated.getPassword());

            patientRepository.save(existing);

            return true;
        }
        return false;
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }


    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }


    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> updatePatientFields(Long id, PatientUpdateRequest updateRequest) {
        Optional<Patient> existingPatientOpt = patientRepository.findById(id);

        if (existingPatientOpt.isPresent()) {
            Patient existingPatient = existingPatientOpt.get();

            if (updateRequest.getPatientName() != null) {
                existingPatient.setPatientName(updateRequest.getPatientName());
            }
            if (updateRequest.getGender() != null) {
                existingPatient.setGender(updateRequest.getGender());
            }
            if (updateRequest.getAge() != null) {
                existingPatient.setAge(updateRequest.getAge());
            }
            if (updateRequest.getAddress() != null) {
                existingPatient.setAddress(updateRequest.getAddress());
            }
            if (updateRequest.getPhoneNumber() != null) {
                existingPatient.setPhoneNumber(updateRequest.getPhoneNumber());
            }
            if (updateRequest.getMedical_state() != null) {
                existingPatient.setMedical_state(updateRequest.getMedical_state());
            }
            if (updateRequest.getAbout_me() != null) {
                existingPatient.setAbout_me(updateRequest.getAbout_me());
            }

            return Optional.of(patientRepository.save(existingPatient));
        } else {
            return Optional.empty(); // Patient not found
        }
    }

    @Override
    public Patient updateAboutme(Long id, String about_me) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + id));
        patient.setAbout_me(about_me);
        return patientRepository.save(patient); // Save the updated patient
    }

    @Override
    public Patient assignFamilyToPatient(Long patientId, Long familyId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + familyId));

        // Set the family on the patient (this is the owning side of the ManyToOne relationship)
        patient.setFamily(family);

        // Save the patient to persist the change. JPA will update the family_id foreign key.
        return patientRepository.save(patient);
    }

    // Optional: Method to remove a family from a patient (set family to null)
    @Override
    public Patient removeFamilyFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        patient.setFamily(null); // Disassociate the family
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Long> getPatientIdByPatientName(String familyname) {
        // Use the new repository method
        return patientRepository.findByPatientName(familyname)
                .map(Patient::getId); // If a doctor is found, map it to its ID
    }

}


