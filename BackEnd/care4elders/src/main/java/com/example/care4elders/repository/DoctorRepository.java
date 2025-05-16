package com.example.care4elders.repository;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

   // Doctor findByDoctorname(String doctorname);


    List<Doctor> findByDoctornameContaining(String name);

    @Query("SELECT d FROM Doctor d WHERE d.doctorname = :name")
    Doctor findByDoctorname(@Param("name") String name);

}
