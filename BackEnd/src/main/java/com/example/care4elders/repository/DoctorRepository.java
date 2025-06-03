package com.example.care4elders.repository;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Family;
import com.example.care4elders.model.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    Optional<Doctor> findByPassword(String password);

    Optional<Doctor> findById(Long id);

    Optional <Doctor> getFamilyById(Long id);

    List<Doctor> findByDoctornameContaining(String name);

    // Add this method to find a doctor by their name
    Optional<Doctor> findByDoctorname(String doctorname);

}
