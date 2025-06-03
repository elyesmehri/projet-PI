package com.example.care4elders.services;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.controllers.DTO.*;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    Doctor save(Doctor doctor);

    boolean deleteById(Long id);

    Doctor addFamilyToDoctor(Long doctorId, Long familyId);

    Doctor getDoctorWithFamilies(Long id);

    Doctor addPatientToDoctor(Long doctorId, Long patientId);

    // You might also want a method to remove a patient from a doctor
    void removePatientFromDoctor(Long doctorId, Long patientId);

    Doctor updatePassword(long id, String password);

    boolean checkDoctorExists(Long id, String medicalID, String password);

    boolean updateDoctor(Long id, Doctor updated);

    Doctor findById(Long id);

    Doctor addDoctor(Doctor doctor);


    List<Doctor> addDoctors(List<Doctor> families);

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);

    void deleteAllDoctors();

    Optional<Doctor> findByName(String name);

    Optional<Doctor> getDoctorsByDoctorId(Long doctorId);

    Doctor removeAppointmentFromDoctor(Long doctorId, Long appointmentId);

    DoctorResponse convertToDto(Doctor doctor);

    DoctorResponse addAffiliatedInsurance(Long doctorId, Long insuranceId);

    DoctorResponse removeAffiliatedInsurance(Long doctorId, Long insuranceId);

    void setAffiliatedInsurances(Doctor doctor, List<Long> insuranceIds);

    Optional<Long> getDoctorIdByDoctorName(String doctorName);
}
