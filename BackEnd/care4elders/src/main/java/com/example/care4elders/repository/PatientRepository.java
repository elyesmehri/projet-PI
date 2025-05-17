package com.example.care4elders.repository;

import com.example.care4elders.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    List<Patient> findByPatientName(String patientName);

    List<Patient> findByDoctor_Doctorname(String doctorname); // Corrected method name

    List<Patient> findByDoctorId(Long id);

    Long countByDoctorId(Long doctorId);

}
