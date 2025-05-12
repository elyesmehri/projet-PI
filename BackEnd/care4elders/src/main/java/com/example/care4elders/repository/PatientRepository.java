package com.example.care4elders.repository;

import com.example.care4elders.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    List<Patient> findByDoctor(String doctor);

    List <Patient> findByPatientName(String patientName);

}
