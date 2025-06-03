package com.example.care4elders.repository;

import com.example.care4elders.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find all appointments associated with a specific doctor
    List<Appointment> findByDoctorId(Long doctorId);

    // Find all appointments associated with a specific family
    List<Appointment> findByFamilyId(Long familyId);
}
