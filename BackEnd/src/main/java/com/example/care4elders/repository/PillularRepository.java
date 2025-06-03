package com.example.care4elders.repository;

import com.example.care4elders.model.Pillular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PillularRepository extends JpaRepository<Pillular, Long> {

    // Custom finder methods if needed
    List<Pillular> findByDoctorId(Long doctorId);
    List<Pillular> findByPatientId(Long patientId);
    List<Pillular> findByMedicationId(Long medicationId);
    List<Pillular> findByInsuranceId(Long insuranceId);
}
