package com.example.care4elders.services;

import com.example.care4elders.model.Doctor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(Long id);
    Doctor addDoctor(Doctor doctor);
    List<Doctor> addDoctorsBulk(List<Doctor> doctors);
    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    boolean validateDoctorLogin(String doctorname, String password);

    void deleteDoctor(Long id);
    void deleteDoctorsBulk(List<Long> ids);
    Boolean checkDoctorCredentials(Map<String, String> credentials);

    boolean authenticateDoctor(String doctorname, String password);

    boolean updateDoctorPassword(String doctorname, String newPassword);

    boolean updatePassword(String doctorname, String password);

    void deleteAllDoctors();


    boolean updateScoreByName(String doctorname, int score);

    Doctor findByName(String doctorname);
}
