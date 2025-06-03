package com.example.care4elders.repository;

import com.example.care4elders.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

public interface VisitRepository  extends JpaRepository <Visit, Long> {

    // Custom finder methods
    List<Visit> findByDoctorId(Long doctorId);
    List<Visit> findByPatientId(Long patientId);
    List<Visit> findByVisitDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Visit> findByDoctorIdAndVisitDateBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

}
