package com.example.care4elders.Implementations;

import com.example.care4elders.model.*;

import com.example.care4elders.repository.*;
import com.example.care4elders.controllers.DTO.*;

import com.example.care4elders.services.DoctorService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository  doctorRepository;
    private final PatientRepository patientRepository;
    private final FamilyRepository familyRepository;
    private final AppointmentRepository appointmentRepository;
    private final InsuranceRepository insuranceRepository;


    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public boolean deleteById(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Doctor addFamilyToDoctor(Long doctorId, Long familyId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId)); // Custom exception or Spring's

        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + familyId));

        doctor.addFamily(family); // Uses the helper method inside the Doctor entity
        return doctorRepository.save(doctor); // Persist the change
    }

    @Override
    public Doctor getDoctorWithFamilies(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + id));
    }

    @Override
    public Doctor addPatientToDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        // Use the helper method defined in the Doctor entity to manage the bidirectional relationship
        doctor.addPatient(patient);

        // Save the Doctor (owning side of the Many-to-Many relationship)
        // This will trigger JPA to insert a record into the doctor_patient join table
        return doctorRepository.save(doctor);
    }

    // You might also want a method to remove a patient from a doctor
    @Override
    public void removePatientFromDoctor(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        doctor.removePatient(patient);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor updatePassword(long id, String password) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id); // Use findById, the standard JPA method

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setPassword(password);
            return doctorRepository.save(doctor);
        } else {

            return null; // Or throw new ResourceNotFoundException("Family not found with id: " + id);
        }
    }


    @Override
    public boolean checkDoctorExists(Long id, String medicalID, String password) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            // IMPORTANT: In a real application, you MUST hash the input 'password'
            // and compare it to a securely hashed password stored in the database.
            // DO NOT compare plain text passwords.
            return doctor.getMedicalID().equals(medicalID) && doctor.getPassword().equals(password);
        }
        return false;
    }
    @Override
    public boolean updateDoctor(Long id, Doctor updated) {

        Optional<Doctor> optional = doctorRepository.findById(id);

        if (optional.isPresent()) {

            Doctor existing = optional.get();

            existing.setDoctorname(updated.getDoctorname());
            existing.setPassword(updated.getPassword());

            doctorRepository.save(existing);

            return true;
        }
        return false;
    }

    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> addDoctors(List<Doctor> families) {
        return doctorRepository.saveAll(families);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public void deleteAllDoctors() {
        doctorRepository.deleteAll();
    }

    @Override
    public Optional<Doctor> findByName(String name) {
        return doctorRepository.findByDoctorname(name);

    }

    @Override
    public Optional<Doctor> getDoctorsByDoctorId(Long doctorId) {

        return doctorRepository.findById(doctorId);
    }

    @Override
    public Doctor removeAppointmentFromDoctor(Long doctorId, Long appointmentId) {
        // 1. Validate if the Doctor exists
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        // 2. Validate if the Appointment exists
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        // 3. Optional but Recommended: Check if the appointment is actually linked to this doctor
        // This prevents unlinking an appointment that wasn't linked to this doctor in the first place.
        if (appointment.getDoctor() == null || !appointment.getDoctor().getId().equals(doctorId)) {
            throw new IllegalStateException("Appointment with ID " + appointmentId + " is not assigned to Doctor with ID " + doctorId);
        }

        // 4. Perform the disassociation: Set the doctor for the appointment to null
        appointment.setDoctor(null);

        // 5. Save the Appointment to persist the null foreign key in the database
        // (Appointment is the owning side of this One-to-Many relationship)
        appointmentRepository.save(appointment);

        // 6. Return the Doctor entity (as per the typical return type for such operations from the Doctor's perspective)
        return doctor;
    }

    @Override
    public DoctorResponse convertToDto(Doctor doctor) {
        DoctorResponse dto = new DoctorResponse();
        dto.setId(doctor.getId());
        dto.setDoctorname(doctor.getDoctorname());
        dto.setMedicalID(doctor.getMedicalID());
        dto.setSpeciality(doctor.getSpeciality());
        dto.setAddress(doctor.getAddress());
        dto.setScore(doctor.getScore());
        dto.setPhonenumber(doctor.getPhonenumber());
        dto.setHospital(doctor.getHospital());
        dto.setNumberofpatients(doctor.getNumberofpatients());
        dto.setGender(doctor.getGender());

        // Map affiliated insurances to simple DTOs
        if (doctor.getAffiliatedInsurances() != null) {
            dto.setAffiliatedInsurances(doctor.getAffiliatedInsurances().stream()
                    .map(insurance -> {
                        InsuranceSimpleResponse simpleDto = new InsuranceSimpleResponse();
                        simpleDto.setId(insurance.getId());
                        simpleDto.setProviderName(insurance.getProviderName());
                        simpleDto.setPolicyNumber(insurance.getPolicyNumber());
                        return simpleDto;
                    })
                    .collect(Collectors.toList()));
        } else {
            dto.setAffiliatedInsurances(new ArrayList<>());
        }


        return dto;
    }

    @Override
    public DoctorResponse addAffiliatedInsurance(Long doctorId, Long insuranceId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));

        if (doctor.getAffiliatedInsurances().contains(insurance)) {
            throw new IllegalStateException("Insurance with ID " + insuranceId + " is already affiliated with doctor " + doctorId);
        }
        doctor.getAffiliatedInsurances().add(insurance);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDto(updatedDoctor);
    }

    // --- 3. The `removeAffiliatedInsurance` method ---
    // This method unlinks an Insurance from a Doctor.
    @Override
    public DoctorResponse removeAffiliatedInsurance(Long doctorId, Long insuranceId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + insuranceId));

        if (!doctor.getAffiliatedInsurances().contains(insurance)) {
            throw new IllegalStateException("Insurance with ID " + insuranceId + " is not currently affiliated with doctor " + doctorId);
        }
        doctor.getAffiliatedInsurances().remove(insurance);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return convertToDto(updatedDoctor);
    }

    @Override
    public void setAffiliatedInsurances(Doctor doctor, List<Long> insuranceIds) {
        if (insuranceIds != null && !insuranceIds.isEmpty()) {
            List<Insurance> insurances = insuranceRepository.findAllById(insuranceIds);
            if (insurances.size() != insuranceIds.size()) {
                throw new EntityNotFoundException("One or more affiliated insurance IDs not found.");
            }
            doctor.getAffiliatedInsurances().clear();
            doctor.getAffiliatedInsurances().addAll(insurances);
        } else {
            doctor.getAffiliatedInsurances().clear();
        }
    }

    @Override
    public Optional<Long> getDoctorIdByDoctorName(String doctorName) {
        // Use the new repository method
        return doctorRepository.findByDoctorname(doctorName)
                .map(Doctor::getId); // If a doctor is found, map it to its ID
    }


}



